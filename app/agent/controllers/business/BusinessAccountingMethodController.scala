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

package agent.controllers.business

import javax.inject.{Inject, Singleton}

import agent.auth.AuthenticatedController
import core.config.BaseControllerConfig
import agent.forms.AccountingMethodForm
import agent.models.AccountingMethodModel
import play.api.data.Form
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request}
import play.twirl.api.Html
import agent.services.KeystoreService
import core.services.AuthService

import scala.concurrent.Future

@Singleton
class BusinessAccountingMethodController @Inject()(val baseConfig: BaseControllerConfig,
                                                   val messagesApi: MessagesApi,
                                                   val keystoreService: KeystoreService,
                                                   val authService: AuthService
                                                  ) extends AuthenticatedController {

  def view(accountingMethodForm: Form[AccountingMethodModel], isEditMode: Boolean)(implicit request: Request[_]): Html =
    agent.views.html.business.accounting_method(
      accountingMethodForm = accountingMethodForm,
      postAction = agent.controllers.business.routes.BusinessAccountingMethodController.submit(editMode = isEditMode),
      isEditMode,
      backUrl = backUrl(isEditMode)
    )

  def show(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      keystoreService.fetchAccountingMethod() map {
        accountingMethod => Ok(view(accountingMethodForm = AccountingMethodForm.accountingMethodForm.fill(accountingMethod), isEditMode = isEditMode))
      }
  }

  def submit(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      AccountingMethodForm.accountingMethodForm.bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest(view(accountingMethodForm = formWithErrors, isEditMode = isEditMode))),
        accountingMethod => {
          keystoreService.saveAccountingMethod(accountingMethod) map (_ => isEditMode match {
            case true => Redirect(agent.controllers.routes.CheckYourAnswersController.show())
            case _ => Redirect(agent.controllers.routes.TermsController.showTerms())
          })
        }
      )
  }

  def backUrl(isEditMode: Boolean): String =
    if (isEditMode)
      agent.controllers.routes.CheckYourAnswersController.show().url
    else
      agent.controllers.business.routes.BusinessNameController.showBusinessName().url

}
