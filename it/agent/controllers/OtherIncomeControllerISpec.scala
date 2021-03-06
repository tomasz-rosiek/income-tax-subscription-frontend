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

import _root_.agent.forms.{IncomeSourceForm, OtherIncomeForm}
import _root_.agent.helpers.ComponentSpecBase
import _root_.agent.helpers.IntegrationTestConstants._
import _root_.agent.helpers.IntegrationTestModels._
import _root_.agent.helpers.servicemocks.{AuthStub, KeystoreStub}
import _root_.agent.models.{IncomeSourceModel, OtherIncomeModel}
import play.api.http.Status._
import play.api.i18n.Messages
import _root_.agent.services.CacheConstants

class OtherIncomeControllerISpec extends ComponentSpecBase {

  "GET /income-other" when {

    "keystore call fails" should {
      "internal server error" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreFailure()

        When("GET /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.otherIncome()

        Then("Should return a INTERNAL_SERVER_ERROR")
        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }
    }

    "keystore returns all data" should {
      "show the other income page with an option selected" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubFullKeystore()

        When("GET /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.otherIncome()

        Then("Should return a OK with the other income page")
        res should have(
          httpStatus(OK),
          pageTitle(Messages("agent.income-other.title")),
          radioButtonSet(id = "choice", selectedRadioButton = Some(Messages("agent.income-other.no")))
        )
      }
    }

    "keystore returns no data for other income and" when {
      "income source is returned" should {
        "show the other income page without an option selected" in {
          Given("I setup the Wiremock stubs")
          AuthStub.stubAuthSuccess()
          KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(testIncomeSourceBoth)))

          When("GET /income-other is called")
          val res = IncomeTaxSubscriptionFrontend.otherIncome()

          Then("Should return a OK with the income source page")
          res should have(
            httpStatus(OK),
            pageTitle(Messages("agent.income-other.title")),
            radioButtonSet(id = "choice", selectedRadioButton = None)
          )
        }
      }

      "Income source is not returned" should {
        "redirect to income source" in {
          Given("I setup the Wiremock stubs")
          AuthStub.stubAuthSuccess()
          KeystoreStub.stubEmptyKeystore()

          When("GET /income-other is called")
          val res = IncomeTaxSubscriptionFrontend.otherIncome()

          Then("Should return a OK with the income source page")
          res should have(
            httpStatus(SEE_OTHER),
            redirectURI(incomeSourceURI)
          )
        }
      }
    }

    "redirect to sign-in when auth fails" in {
      Given("I setup the Wiremock stubs")
      AuthStub.stubUnauthorised()

      When("GET /income-other is called")
      val res = IncomeTaxSubscriptionFrontend.otherIncome()

      Then("Should return a SEE_OTHER with a redirect location of sign-in")
      res should have(
        httpStatus(SEE_OTHER),
        redirectURI(signInURI)
      )
    }
  }

  "POST /client/income-other" when {

    "not in edit mode" should {

      "if income source is not in key store redirect to income page" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_business)
        val keystoreOtherIncome = OtherIncomeModel(OtherIncomeForm.option_no)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = None, otherIncome = Some(keystoreOtherIncome)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of income source")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(incomeSourceURI)
        )
      }

      "select the Yes other income radio button on the other income page" in {
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(testIncomeSourceBoth)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of error other income")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(errorOtherIncomeURI)
        )
      }

      "select the No other income radio button on the other income page while on Business journey" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_business)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_no)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(keystoreIncomeSource)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of accounting period prior")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(accountingPeriodPriorURI)
        )
      }

      "select the No other income radio button on the other income page while on Both journey" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_both)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_no)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(keystoreIncomeSource)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of accounting period prior")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(accountingPeriodPriorURI)
        )
      }

      "select the No other income radio button on the other income page while on Property journey" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_property)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_no)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(keystoreIncomeSource)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of terms")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(termsURI)
        )
      }

      "income source is not present" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubEmptyKeystore()
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, testIncomeSourceBoth)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, None)

        Then("Should redirect user to income source")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(incomeSourceURI)
        )
        KeystoreStub.verifyKeyStoreSave(CacheConstants.OtherIncome, "", Some(0))
      }

      "not select an option on the other income page" in {
        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(testIncomeSourceBoth)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, "")

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, None)

        Then("Should return a BAD_REQUEST and display an error box on screen without redirecting")
        res should have(
          httpStatus(BAD_REQUEST),
          errorDisplayed()
        )
      }

      "select invalid other income option on the other income page as if the user it trying to manipulate the html" in {
        val userInput = OtherIncomeModel("madeup")

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(testIncomeSourceBoth)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, "madeup")

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a BAD_REQUEST and display an error box on screen without redirecting")
        res should have(
          httpStatus(BAD_REQUEST),
          errorDisplayed()
        )
      }

      "keystore call fails" in {
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreFailure()

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a INTERNAL_SERVER_ERROR")
        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }

      "redirect to sign-in when auth fails" in {
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubUnauthorised()

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = false, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of sign-in")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(signInURI)
        )
      }
    }

    "in edit mode" should {

      "if income source is not in key store redirect to income page" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_business)
        val keystoreOtherIncome = OtherIncomeModel(OtherIncomeForm.option_no)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = None, otherIncome = Some(keystoreOtherIncome)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = true, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of income source")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(incomeSourceURI)
        )
      }

      "changing to the Yes other income radio button on the other income page" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_business)
        val keystoreOtherIncome = OtherIncomeModel(OtherIncomeForm.option_no)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(keystoreIncomeSource), otherIncome = Some(keystoreOtherIncome)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = true, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of error other income")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(errorOtherIncomeURI)
        )
      }

      "simulate not changing other income when already selected no on the other income page" in {
        val keystoreIncomeSource = IncomeSourceModel(IncomeSourceForm.option_business)
        val keystoreOtherIncome = OtherIncomeModel(OtherIncomeForm.option_no)
        val userInput = OtherIncomeModel(OtherIncomeForm.option_no)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreData(keystoreData(incomeSource = Some(keystoreIncomeSource), otherIncome = Some(keystoreOtherIncome)))
        KeystoreStub.stubKeystoreSave(CacheConstants.OtherIncome, userInput)

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = true, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of check your answers")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(checkYourAnswersURI)
        )
      }

      "keystore call fails" in {
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubAuthSuccess()
        KeystoreStub.stubKeystoreFailure()

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = true, Some(userInput))

        Then("Should return a INTERNAL_SERVER_ERROR")
        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }

      "redirect to sign-in when auth fails" in {
        val userInput = OtherIncomeModel(OtherIncomeForm.option_yes)

        Given("I setup the Wiremock stubs")
        AuthStub.stubUnauthorised()

        When("POST /income-other is called")
        val res = IncomeTaxSubscriptionFrontend.submitOtherIncome(inEditMode = true, Some(userInput))

        Then("Should return a SEE_OTHER with a redirect location of sign-in")
        res should have(
          httpStatus(SEE_OTHER),
          redirectURI(signInURI)
        )
      }
    }
  }
}