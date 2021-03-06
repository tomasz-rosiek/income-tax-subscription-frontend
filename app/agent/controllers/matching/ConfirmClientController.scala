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

package agent.controllers.matching

import javax.inject.{Inject, Singleton}

import agent.auth.AgentJourneyState._
import agent.auth.{AgentUserMatched, IncomeTaxAgentUser, UserMatchingController}
import core.config.BaseControllerConfig
import agent.connectors.models.matching.{LockedOut, NotLockedOut}
import agent.controllers.ITSASessionKeys
import agent.models.agent.ClientDetailsModel
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request, Result}
import play.twirl.api.Html
import agent.services._
import core.services.AuthService
import uk.gov.hmrc.http.InternalServerException

import scala.concurrent.Future
import scala.util.Left


@Singleton
class ConfirmClientController @Inject()(val baseConfig: BaseControllerConfig,
                                        val messagesApi: MessagesApi,
                                        val keystoreService: KeystoreService,
                                        val agentQualificationService: AgentQualificationService,
                                        val authService: AuthService,
                                        val lockOutService: AgentLockoutService
                                       ) extends UserMatchingController {

  def view(clientDetailsModel: ClientDetailsModel)(implicit request: Request[_]): Html =
    agent.views.html.check_your_client_details(
      clientDetailsModel,
      routes.ConfirmClientController.submit(),
      backUrl
    )

  private def handleLockOut(f: => Future[Result])(implicit user: IncomeTaxAgentUser, request: Request[_]) = {
    (lockOutService.getLockoutStatus(user.arn.get) flatMap {
      case Right(NotLockedOut) => f
      case Right(_: LockedOut) =>
        Future.successful(Redirect(agent.controllers.matching.routes.ClientDetailsLockoutController.show().url))
    }).recover { case e =>
      throw new InternalServerException("client details controller: " + e)
    }
  }

  def show(): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      handleLockOut {
        keystoreService.fetchClientDetails() map {
          case Some(clientDetails) => Ok(view(clientDetails))
          case _ => Redirect(agent.controllers.matching.routes.ClientDetailsController.show())
        }
      }
  }

  def submit(): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      handleLockOut {
        val arn = user.arn.get // Will fail if no ARN in user

        import ITSASessionKeys.FailedClientMatching

        import scala.concurrent.Future.{failed, successful}

        agentQualificationService.orchestrateAgentQualification(arn).flatMap {
          case Left(NoClientDetails) => successful(Redirect(routes.ClientDetailsController.show()))
          case Left(NoClientMatched) =>
            val currentCount = request.session.get(FailedClientMatching).fold(0)(_.toInt)
            val incCount = currentCount + 1
            if (incCount < applicationConfig.matchingAttempts) {
              successful(Redirect(agent.controllers.matching.routes.ClientDetailsErrorController.show())
                .addingToSession(FailedClientMatching -> incCount.toString))
            }
            else {
              for {
                _ <- lockOutService.lockoutAgent(arn)
                _ <- keystoreService.deleteAll()
              } yield
                Redirect(agent.controllers.matching.routes.ClientDetailsLockoutController.show()).removingFromSession(FailedClientMatching)
            }
          case Left(ClientAlreadySubscribed) => successful(Redirect(agent.controllers.routes.ClientAlreadySubscribedController.show())
            .removingFromSession(FailedClientMatching))
          case Left(NoClientRelationship) => successful(Redirect(agent.controllers.routes.NoClientRelationshipController.show())
            .removingFromSession(FailedClientMatching))
          case Right(_) => successful(Redirect(agent.controllers.routes.HomeController.index()).withJourneyState(AgentUserMatched)
            .removingFromSession(FailedClientMatching))
        }.recoverWith {
          case e => failed(new InternalServerException("ConfirmClientController.submit\n" + e.getMessage))
        }
      }
  }

  lazy val backUrl: String = routes.ClientDetailsController.show().url

}
