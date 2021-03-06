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

package agent.controllers

import agent.audit.Logging
import agent.forms.OtherIncomeForm
import agent.models.OtherIncomeModel
import agent.services.mocks.MockKeystoreService
import agent.utils.TestModels
import org.jsoup.Jsoup
import play.api.http.Status
import play.api.mvc.{Action, AnyContent}
import play.api.test.FakeRequest
import play.api.test.Helpers.{await, _}

class OtherIncomeErrorControllerSpec extends AgentControllerBaseSpec with MockKeystoreService {

  override val controllerName: String = "OtherIncomeErrorController"
  override val authorisedRoutes: Map[String, Action[AnyContent]] = Map()

  object TestOtherIncomeErrorController extends OtherIncomeErrorController()(
    MockBaseControllerConfig,
    messagesApi,
    MockKeystoreService,
    mockAuthService,
    app.injector.instanceOf[Logging]
  )

  "Calling the showOtherIncomeError action of the OtherIncomeErrorController" should {

    lazy val result = TestOtherIncomeErrorController.showOtherIncomeError(FakeRequest())
    lazy val document = Jsoup.parse(contentAsString(result))

    "return 200" in {
      status(result) must be(Status.OK)
    }

    "return HTML" in {
      contentType(result) must be(Some("text/html"))
      charset(result) must be(Some("utf-8"))
    }

  }

  "Calling the submitOtherIncomeError action of the OtherIncomeError controller with an authorised user" should {

    def callSubmit = TestOtherIncomeErrorController.submitOtherIncomeError(subscriptionRequest
      .post(OtherIncomeForm.otherIncomeForm, OtherIncomeModel(OtherIncomeForm.option_no)))

    "return a redirect status (SEE_OTHER - 303)" in {

      setupMockKeystore(fetchIncomeSource = TestModels.testIncomeSourceBusiness)

      val goodRequest = callSubmit

      status(goodRequest) must be(Status.SEE_OTHER)

      await(goodRequest)
      verifyKeystore(fetchIncomeSource = 1)
    }

    s"redirect to '${agent.controllers.business.routes.BusinessAccountingPeriodPriorController.show().url}' on the business journey" in {

      setupMockKeystore(fetchIncomeSource = TestModels.testIncomeSourceBusiness)

      val goodRequest = callSubmit

      redirectLocation(goodRequest) mustBe Some(agent.controllers.business.routes.BusinessAccountingPeriodPriorController.show().url)

      await(goodRequest)
      verifyKeystore(fetchIncomeSource = 1)
    }

    s"redirect to '${agent.controllers.routes.TermsController.showTerms().url}' on the property journey" in {

      setupMockKeystore(fetchIncomeSource = TestModels.testIncomeSourceProperty)

      val goodRequest = callSubmit

      redirectLocation(goodRequest) mustBe Some(agent.controllers.routes.TermsController.showTerms().url)

      await(goodRequest)
      verifyKeystore(fetchIncomeSource = 1)
    }

    s"redirect to '${agent.controllers.business.routes.BusinessAccountingPeriodPriorController.show().url}' on the both journey" in {

      setupMockKeystore(fetchIncomeSource = TestModels.testIncomeSourceBoth)

      val goodRequest = callSubmit

      redirectLocation(goodRequest) mustBe Some(agent.controllers.business.routes.BusinessAccountingPeriodPriorController.show().url)

      await(goodRequest)
      verifyKeystore(fetchIncomeSource = 1)
    }

  }

  authorisationTests()
}

