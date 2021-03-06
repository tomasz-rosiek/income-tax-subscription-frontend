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

package usermatching.controllers

import javax.inject.{Inject, Singleton}

import core.ITSASessionKeys
import core.ITSASessionKeys._
import core.audit.Logging
import core.auth.JourneyState._
import core.auth._
import core.config.BaseControllerConfig
import core.services.{AuthService, KeystoreService}
import core.utils.Implicits._
import incometax.subscription.models.SubscriptionSuccess
import incometax.subscription.services.SubscriptionService
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request, Result}
import uk.gov.hmrc.http.InternalServerException
import usermatching.models.CitizenDetailsSuccess
import usermatching.services.CitizenDetailsService

import scala.concurrent.Future

@Singleton
class HomeController @Inject()(override val baseConfig: BaseControllerConfig,
                               override val messagesApi: MessagesApi,
                               subscriptionService: SubscriptionService,
                               keystoreService: KeystoreService,
                               val authService: AuthService,
                               citizenDetailsService: CitizenDetailsService,
                               logging: Logging
                              ) extends StatelessController {

  lazy val showGuidance: Boolean = baseConfig.applicationConfig.showGuidance

  def home: Action[AnyContent] = Action { implicit request =>
    val redirect = routes.HomeController.index()

    showGuidance match {
      case true =>
        Ok(views.html.frontpage(redirect))
      case _ =>
        Redirect(redirect)
    }
  }

  private def checkCID(defaultAction: => Future[Result])(implicit user: IncomeTaxSAUser, request: Request[AnyContent]): Future[Result] = {

    lazy val error = Future.failed(new InternalServerException("HomeController.checkCID: unexpected error calling the citizen details service"))

    (user.nino, user.utr) match {
      case (Some(_), Some(_)) => defaultAction
      case (Some(nino), None) =>
        if (request.isInState(UserMatched)) gotoRegistration
        else {
          citizenDetailsService.lookupUtr(nino).flatMap {
            case Right(optResult) =>
              optResult match {
                case Some(CitizenDetailsSuccess(optUtr@Some(utr))) => defaultAction.flatMap(_.addingToSession(ITSASessionKeys.UTR -> utr))
                case Some(CitizenDetailsSuccess(None)) => gotoRegistration
                case _ => error
              }
            case _ => error
          }.recoverWith { case _ => error }
        }
      case (None, _) => // n.b. This should not happen as the user have been redirected by the no nino predicates
        Future.failed(new InternalServerException("HomeController.checkCID: unexpected user state, the user has a utr but no nino"))
    }
  }

  private def gotoRegistration(implicit request: Request[AnyContent]) = Future.successful(
    if (applicationConfig.enableRegistration) {
      val timestamp: String = java.time.LocalDateTime.now().toString

      gotoPreferences
        .addingToSession(StartTime -> timestamp)
        .withJourneyState(Registration)
    }
    else {
      Redirect(routes.NoSAController.show()).removingFromSession(ITSASessionKeys.JourneyStateKey)
    }
  )

  private def checkAlreadySubscribed(default: => Future[Result])(implicit user: IncomeTaxSAUser, request: Request[AnyContent]): Future[Result] =
    subscriptionService.getSubscription(user.nino.get).flatMap {
      case Right(None) => default
      case Right(Some(SubscriptionSuccess(mtditId))) =>
        keystoreService.saveSubscriptionId(mtditId) map { _ =>
          Redirect(incometax.subscription.controllers.routes.ClaimSubscriptionController.claim()).withJourneyState(SignUp)
        }
      case _ =>
        Future.failed(new InternalServerException(s"HomeController.index: unexpected error calling the subscription service"))
    }

  def index: Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      val timestamp: String = java.time.LocalDateTime.now().toString
      checkCID(
        checkAlreadySubscribed(
          gotoPreferences.addingToSession(StartTime -> timestamp).withJourneyState(SignUp)
        )
      )
  }

  lazy val gotoPreferences = Redirect(digitalcontact.controllers.routes.PreferencesController.checkPreferences())
}
