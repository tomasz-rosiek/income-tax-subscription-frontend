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

package controllers.business

import javax.inject.{Inject, Singleton}

import auth.SignUpController
import config.BaseControllerConfig
import forms.AccountingPeriodPriorForm
import models.{AccountingPeriodPriorModel, OtherIncomeModel}
import play.api.data.Form
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request, Result}
import play.twirl.api.Html
import services.{AuthService, KeystoreService}
import utils.Implicits._

import scala.concurrent.Future

@Singleton
class BusinessAccountingPeriodPriorController @Inject()(val baseConfig: BaseControllerConfig,
                                                        val messagesApi: MessagesApi,
                                                        val keystoreService: KeystoreService,
                                                        val authService: AuthService
                                                       ) extends SignUpController {

  def view(accountingPeriodPriorForm: Form[AccountingPeriodPriorModel], isEditMode: Boolean)(implicit request: Request[_]): Future[Html] =
      views.html.business.accounting_period_prior(
        accountingPeriodPriorForm = accountingPeriodPriorForm,
        postAction = controllers.business.routes.BusinessAccountingPeriodPriorController.submit(editMode = isEditMode),
        backUrl = backUrl,
        isEditMode = isEditMode
      )

  def show(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      keystoreService.fetchAccountingPeriodPrior().flatMap { x =>
        view(AccountingPeriodPriorForm.accountingPeriodPriorForm.fill(x), isEditMode = isEditMode).flatMap(view => Ok(view))
      }
  }

  def submit(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      AccountingPeriodPriorForm.accountingPeriodPriorForm.bindFromRequest.fold(
        formWithErrors => view(formWithErrors, isEditMode = isEditMode).flatMap(view => BadRequest(view)),
        accountingPeriodPrior =>
          keystoreService.fetchAccountingPeriodPrior().flatMap {
            somePreviousAnswer =>
              keystoreService.saveAccountingPeriodPrior(accountingPeriodPrior) flatMap { _ =>
                if (somePreviousAnswer.fold(false)(previousAnswer => previousAnswer.equals(accountingPeriodPrior)) && isEditMode)
                  Redirect(incometax.subscription.controllers.routes.CheckYourAnswersController.show())
                else
                  accountingPeriodPrior.currentPeriodIsPrior match {
                    case AccountingPeriodPriorForm.option_yes => yes
                    case AccountingPeriodPriorForm.option_no => no
                  }
              }
          }
      )
  }

  def yes(implicit request: Request[_]): Future[Result] = Redirect(controllers.business.routes.RegisterNextAccountingPeriodController.show())

  def no(implicit request: Request[_]): Future[Result] = Redirect(controllers.business.routes.BusinessAccountingPeriodDateController.show())

  lazy val backUrl = controllers.business.routes.BusinessNameController.show().url

}
