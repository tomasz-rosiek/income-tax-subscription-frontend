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
import agent.forms._
import agent.models.AccountingPeriodModel
import agent.models.enums._
import agent.services.KeystoreService
import core.config.BaseControllerConfig
import core.services.AuthService
import core.utils.Implicits._
import play.api.data.Form
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent, Request}
import play.twirl.api.Html
import uk.gov.hmrc.http.InternalServerException

import scala.concurrent.Future

@Singleton
class BusinessAccountingPeriodDateController @Inject()(val baseConfig: BaseControllerConfig,
                                                       val messagesApi: MessagesApi,
                                                       val keystoreService: KeystoreService,
                                                       val authService: AuthService
                                                      ) extends AuthenticatedController {

  def view(form: Form[AccountingPeriodModel], backUrl: String, isEditMode: Boolean, viewType: AccountingPeriodViewType)(implicit request: Request[_]): Html =
    agent.views.html.business.accounting_period_date(
      form,
      agent.controllers.business.routes.BusinessAccountingPeriodDateController.submitAccountingPeriod(editMode = isEditMode),
      isEditMode,
      backUrl,
      viewType
    )

  def showAccountingPeriod(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user =>
      for {
        accountingPeriod <- keystoreService.fetchAccountingPeriodDate()
        backUrl <- backUrl(isEditMode)
        viewType <- whichView
      } yield
        Ok(view(
          AccountingPeriodDateForm.accountingPeriodDateForm.fill(accountingPeriod),
          backUrl = backUrl,
          isEditMode = isEditMode,
          viewType = viewType
        ))
  }

  def submitAccountingPeriod(isEditMode: Boolean): Action[AnyContent] = Authenticated.async { implicit request =>
    implicit user => {
      whichView.flatMap {
        viewType =>
          AccountingPeriodDateForm.accountingPeriodDateForm.bindFromRequest().fold(
            formWithErrors => backUrl(isEditMode).map(backUrl => BadRequest(view(
              form = formWithErrors,
              backUrl = backUrl,
              isEditMode = isEditMode,
              viewType = viewType
            ))),
            accountingPeriod =>
              keystoreService.saveAccountingPeriodDate(accountingPeriod) map (_ =>
                if (isEditMode)
                  Redirect(agent.controllers.routes.CheckYourAnswersController.show())
                else
                  Redirect(agent.controllers.business.routes.BusinessNameController.showBusinessName())
                )
          )
      }
    }
  }

  def whichView(implicit request: Request[_]): Future[AccountingPeriodViewType] = {

    keystoreService.fetchAccountingPeriodPrior().flatMap {
      case Some(currentPeriodPrior) =>
        currentPeriodPrior.currentPeriodIsPrior match {
          case AccountingPeriodPriorForm.option_yes =>
            NextAccountingPeriodView
          case AccountingPeriodPriorForm.option_no =>
            CurrentAccountingPeriodView
        }
      case _ => new InternalServerException(s"Internal Server Error - No Accounting Period Prior answer retrieved from keystore")
    }
  }

  def backUrl(isEditMode: Boolean)(implicit request: Request[_]): Future[String] = {

    if (isEditMode)
      agent.controllers.routes.CheckYourAnswersController.show().url
    else
      keystoreService.fetchAccountingPeriodPrior() flatMap {
        case Some(currentPeriodPrior) => currentPeriodPrior.currentPeriodIsPrior match {
          case AccountingPeriodPriorForm.option_yes =>
            agent.controllers.business.routes.RegisterNextAccountingPeriodController.show().url
          case AccountingPeriodPriorForm.option_no =>
            agent.controllers.business.routes.BusinessAccountingPeriodPriorController.show().url
        }
        case _ => new InternalServerException(s"Internal Server Error - No Accounting Period Prior answer retrieved from keystore")
      }
  }

}
