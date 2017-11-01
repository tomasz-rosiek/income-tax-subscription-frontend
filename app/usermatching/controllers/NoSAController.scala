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

import core.config.AppConfig
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.frontend.controller.FrontendController

@Singleton
class NoSAController @Inject()(implicit val applicationConfig: AppConfig,
                               val messagesApi: MessagesApi
                                ) extends FrontendController with I18nSupport {

  val show: Action[AnyContent] = Action {
    implicit request => Ok(usermatching.views.html.no_sa(postAction = routes.NoSAController.submit()))
  }

  val submit: Action[AnyContent] = Action {
    implicit request => Redirect(core.controllers.routes.SignOutController.signOut())
  }

}