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

package incometax.subscription.controllers

import helpers.ComponentSpecBase
import helpers.IntegrationTestConstants.testMTDID
import helpers.servicemocks._
import play.api.http.Status._
import play.api.libs.json.Json.toJson
import core.services.CacheConstants.MtditId


class ClaimSubscriptionControllerISpec extends ComponentSpecBase {
  "POST /report-quarterly/income-and-expenses/sign-up/claim-subscription" should {
    "show the check your answers page" in {
      Given("I setup the Wiremock stubs")
      AuthStub.stubAuthSuccess()
      KeystoreStub.stubKeystoreData(Map(MtditId -> toJson(testMTDID)))
      GGConnectorStub.stubEnrolResult(OK)
      GGAuthenticationStub.stubRefreshProfileResult(NO_CONTENT)

      When("GET /claim-subscription is called")
      val res = IncomeTaxSubscriptionFrontend.claimSubscription()

      Then("Should return an OK status")
      res should have(
        httpStatus(OK)
      )
    }
  }
}
