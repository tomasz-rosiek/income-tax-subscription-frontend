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

package controllers

import java.time.LocalDate
import javax.inject.{Inject, Singleton}

import cats.data.EitherT
import cats.implicits._
import config.BaseControllerConfig
import connectors.models.{ConnectorError, KeystoreMissingError}
import models.DateModel._
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request, Result}
import services.CacheConstants.MtditId
import services.{AuthService, KeystoreService, SubscriptionOrchestrationService}
import uk.gov.hmrc.play.http.{HeaderCarrier, InternalServerException}

import scala.concurrent.Future

@Singleton
class ClaimSubscriptionController @Inject()(val baseConfig: BaseControllerConfig,
                                            val messagesApi: MessagesApi,
                                            val authService: AuthService,
                                            val subscriptionOrchestrationService: SubscriptionOrchestrationService,
                                            val keystoreService: KeystoreService
                                           ) extends AuthenticatedController {
  val claim: Action[AnyContent] = Authenticated.async {
    implicit request =>
      user =>
        val res = for {
          mtditId <- EitherT(getMtditId())
          nino = user.nino.get
          subscriptionResult <- EitherT(subscriptionOrchestrationService.enrolAndRefresh(mtditId, nino))
        } yield Ok(confirmationPage(mtditId))

        res.valueOr(ex => throw new InternalServerException(ex.toString))
  }

  private def getMtditId()(implicit hc: HeaderCarrier): Future[Either[ConnectorError, String]] =
    keystoreService.fetchSubscriptionId() map (_.toRight(left = KeystoreMissingError(MtditId)))

  private def confirmationPage(id: String)(implicit request: Request[AnyContent]) = views.html.enrolled.already_enrolled(
    subscriptionId = id,
    routes.ConfirmationController.signOut()
   )
}
