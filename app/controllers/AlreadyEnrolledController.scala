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

import javax.inject.Inject

import config.{AppConfig, BaseControllerConfig}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import services.KeystoreService
import uk.gov.hmrc.play.frontend.controller.FrontendController

import scala.concurrent.Future


class AlreadyEnrolledController @Inject()(val baseConfig: BaseControllerConfig,
                                         val messagesApi: MessagesApi
                                        ) extends BaseController {

  val enrolled = Authorised.asyncForEnrolled {  implicit user =>
    implicit request =>
      Future.successful(Ok(views.html.enrolled.already_enrolled(postAction = controllers.routes.SignOutController.signOut())))
  }

}
