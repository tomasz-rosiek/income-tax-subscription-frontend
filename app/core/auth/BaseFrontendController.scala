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

package core.auth

import core.auth.AuthPredicate._
import core.auth.JourneyState.{RequestFunctions, SessionFunctions}
import core.config.BaseControllerConfig
import core.services.AuthService
import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc._
import uk.gov.hmrc.auth.core.Retrievals._
import uk.gov.hmrc.auth.core.retrieve.~
import uk.gov.hmrc.auth.core.{AffinityGroup, Enrolments}
import uk.gov.hmrc.play.frontend.controller.FrontendController

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait BaseFrontendController extends FrontendController with I18nSupport with AuthPredicates {

  val authService: AuthService
  val baseConfig: BaseControllerConfig

  override lazy implicit val applicationConfig = baseConfig.applicationConfig

  type ActionBody[User <: IncomeTaxUser] = Request[AnyContent] => User => Future[Result]
  type AuthenticatedAction[User <: IncomeTaxUser] = ActionBody[User] => Action[AnyContent]

  protected trait AuthenticatedActions[User <: IncomeTaxUser] {

    def userApply : (Enrolments, Option[AffinityGroup]) => User

    def apply(action: Request[AnyContent] => User => Result): Action[AnyContent] = async(action andThen (_ andThen Future.successful))

    protected def asyncInternal(predicate: AuthPredicate[User])(action: ActionBody[User]): Action[AnyContent] =
      Action.async { implicit request =>
        authService.authorised().retrieve(allEnrolments and affinityGroup) {
          case enrolments ~ affinity =>
            implicit val user = userApply(enrolments, affinity)

            predicate.apply(request)(user) match {
              case Right(AuthPredicateSuccess) => action(request)(user)
              case Left(failureResult) => failureResult
            }
        }
      }

    def async: AuthenticatedAction[User]
  }

  implicit class FormUtil[T](form: Form[T]) {
    def fill(data: Option[T]): Form[T] = data.fold(form)(form.fill)
  }

  implicit val cacheSessionFunctions: (Session) => SessionFunctions = JourneyState.SessionFunctions
  implicit val cacheRequestFunctions: (Request[_]) => RequestFunctions = JourneyState.RequestFunctions

}

