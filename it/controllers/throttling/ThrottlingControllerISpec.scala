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

package controllers.throttling

import helpers.ComponentSpecBase
import helpers.IntegrationTestConstants.{signInURI, signOutURI}
import helpers.servicemocks.AuthStub
import play.api.http.Status.{OK, SEE_OTHER}
import play.api.i18n.Messages

class ThrottlingControllerISpec extends ComponentSpecBase {

  "GET /report-quarterly/income-and-expenses/sign-up/error/maintenance" when {

    "keystore not applicable" should {
      "show the error maintenance page" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()

        When("GET /error/maintenance is called")
        val res = IncomeTaxSubscriptionFrontend.maintenance()

        Then("Should return a OK with the error main income page")
        res should have(
          httpStatus(OK),
          pageTitle(Messages("throttle_limit.title"))
        )
      }
    }

    "redirect to sign-in when auth fails" in {
      Given("I setup the Wiremock stubs")
      AuthStub.stubUnauthorised()

      When("GET /error/maintenance is called")
      val res = IncomeTaxSubscriptionFrontend.maintenance()

      Then("Should return a SEE_OTHER with a redirect location of sign-in")
      res should have(
        httpStatus(SEE_OTHER),
        redirectURI(signInURI)
      )
    }
  }

  "POST /report-quarterly/income-and-expenses/sign-up/error/maintenance" when {

    "always" should {
      "proceed to sign out" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()

        When("POST /error/maintenance is called")
        val res = IncomeTaxSubscriptionFrontend.submitMaintenance()

        Then("Should return a SEE_OTHER with a redirect location of sign out")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(signOutURI)
        )
      }

      "redirect to sign-in when auth fails" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubUnauthorised()

        When("POST /error/maintenance is called")
        val res = IncomeTaxSubscriptionFrontend.submitMaintenance()

        Then("Should return a SEE_OTHER with a redirect location of sign-in")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(signInURI)
        )
      }
    }
  }

}