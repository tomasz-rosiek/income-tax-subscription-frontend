/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package agent.services

import javax.inject.{Inject, Singleton}

import agent.audit.AuditingService
import agent.audit.models.ClientMatchingAuditing.ClientMatchingAuditModel
import agent.connectors.models.subscription.SubscriptionSuccess
import agent.models.agent.ClientDetailsModel
import core.utils.Implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import uk.gov.hmrc.http.HeaderCarrier

sealed trait UnqualifiedAgent

case object NoClientDetails extends UnqualifiedAgent

case object NoClientMatched extends UnqualifiedAgent

case object ClientAlreadySubscribed extends UnqualifiedAgent

case object UnexpectedFailure extends UnqualifiedAgent

case object NoClientRelationship extends UnqualifiedAgent

case class ApprovedAgent(clientNino: String)

@Singleton
class AgentQualificationService @Inject()(clientMatchingService: ClientMatchingService,
                                          clientRelationshipService: ClientRelationshipService,
                                          subscriptionService: SubscriptionService,
                                          keystoreService: KeystoreService,
                                          auditingService: AuditingService) {

  type ReturnType = Either[UnqualifiedAgent, ApprovedAgent]

  private[services]
  def matchClient(arn: String)(implicit hc: HeaderCarrier): Future[ReturnType] = {
    val clientDetails: Future[Either[UnqualifiedAgent, ClientDetailsModel]] = keystoreService.fetchClientDetails()
      .collect {
        case Some(cd) => Right(cd)
        case _ => Left(NoClientDetails)
      }
      .recoverWith { case _ => Left(UnexpectedFailure) }

    clientDetails.flatMap {
      case Left(x) => Left(x)
      case Right(cd) =>
        clientMatchingService.matchClient(cd)
          .collect {
            case Some(nino) =>
              auditingService.audit(ClientMatchingAuditModel(arn, cd, isSuccess = true), agent.controllers.matching.routes.ConfirmClientController.submit().url)
              Right(ApprovedAgent(nino))
            case None =>
              auditingService.audit(ClientMatchingAuditModel(arn, cd, isSuccess = false), agent.controllers.matching.routes.ConfirmClientController.submit().url)
              Left(NoClientMatched)
          }
          .recoverWith { case _ => Left(UnexpectedFailure) }
    }
  }

  private[services]
  def checkExistingSubscription(matchedClient: ApprovedAgent)(implicit hc: HeaderCarrier): Future[ReturnType] = {
    for {
      agentClientResponse <- subscriptionService.getSubscription(matchedClient.clientNino)
        .collect {
          case Right(None) => Right(matchedClient)
          case Right(Some(SubscriptionSuccess(_))) => Left(ClientAlreadySubscribed)
        }
    } yield agentClientResponse
  }.recoverWith { case _ => Left(UnexpectedFailure) }

  private[services]
  def checkClientRelationship(arn: String,
                              matchedClient: ApprovedAgent
                             )(implicit hc: HeaderCarrier): Future[ReturnType] = {
    for {
      isPreExistingRelationship <- clientRelationshipService.isPreExistingRelationship(arn, matchedClient.clientNino)
    } yield
      if (isPreExistingRelationship) Right(matchedClient)
      else Left(NoClientRelationship)
  }.recoverWith { case _ => Left(UnexpectedFailure) }

  private implicit class Util[A, B](first: Future[Either[A, B]]) {
    def flatMapRight(next: B => Future[Either[A, B]]): Future[Either[A, B]] =
      first.flatMap {
        case Right(v) => next(v)
        case left => left
      }
  }

  def orchestrateAgentQualification(arn: String)(implicit hc: HeaderCarrier): Future[ReturnType] =
    matchClient(arn)
      .flatMapRight(checkExistingSubscription)
      .flatMapRight(checkClientRelationship(arn, _))
      .flatMapRight {
        case returnValue@ApprovedAgent(nino) =>
          keystoreService.saveMatchedNino(nino).flatMap(_ => Future.successful(Right(returnValue)))
      }

}


