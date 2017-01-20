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

package assets

object MessageLookup {

  object Base {
    val continue = "Continue"
    val submit = "Submit"
    val startDate = "Start date"
    val endDate = "End date"
    val day = "Day"
    val month = "Month"
    val year = "Year"
    val errorHeading = "Error Summary"
    val chage = "Change"
    val where_can_i_get_this_information = "Where can I get this information"
  }

  object HelloWorld {
    val title = "Hello from income-tax-subscription-frontend"
    val heading = "Hello from income-tax-subscription-frontend !"
  }

  object AccountingPeriod {
    val title = "What is your accounting period?"
    val heading = "What is your accounting period?"
  }

  object ContactEmail {
    val title = "What is your contact email address?"
    val heading: String = title
  }

  object BusinessName {
    val title = "What is your business name?"
    val heading: String = title
    val hint = "This does not mean trading name"
  }

  object BusinessIncomeType {
    val title = "How do you receive your business income?"
    val heading: String = title
    val cash = "Cash"
    val accruals = "Accruals"
  }

  object Terms {
    val title = "Terms"
    val heading: String = title
  }

  object Summary {
    val title = "Check your answers"
    val heading: String = title
    val accounting_period = "Business accounting period"
    val accounting_period_month: Int => String = (month: Int) => s"$month month period"
    val business_name = "Business name"
    val income_type = "Income type"
    val contact_email = "Contact email"
    val terms = "Terms and conditions"
    val terms_agreed = "Read and agreed"
  }

  object Timeout {
    val title = "Session closed due to inactivity"
    val heading = "You've been signed out due to inactivity."
    val returnToHome = """You can start again from the <a href="{0}" rel="external">subscription</a> page."""
  }

  object Confirmation {
    val title = "Submitted"
    val heading: String = title
    val submissionReferenceLabel = "Submission number:"
    val submissionDateLabel = "Date:"
    val emailConfirmation = "You'll receive an email confirming that your application has been received."

    object whatHappensNext {
      val heading = "What happens next"
      val para1 = "Your process for HMRC."
      val bullet1 = "What correspondence user will expect to receive and within what time period"
      val bullet2 = "What implications and obligations on the user as a result of this subscription if any"
    }

    object registerForMoreTax {
      val heading = "Register for more tax"
      val link1 = "PAYE (Pay as you earn)"
      val link2 = "VAT (Value added tax)"
    }

    object guidanceSection {
      val heading = "Guidance"
      val link1 = "Quarterly filing instructions"
      val link2 = "Downloading software"
      val link3 = "Further reading"
    }

    object giveUsFeedback {
      val heading = "Give us feedback"
      val link1 = "What did you think of this service?"
      val feedbackDuration = "(takes 30 seconds)"
    }

  }

  object Error {

    object Date {
      val empty = "Please enter a date"
      val invalid = "Please enter a valid date"
      val end_violation = "The end date must be after the start date"
    }

    object BusinessAccountingPeriod {
      val minStartDate = "You can't enter a start date that's before 1 April 2017"

      def maxEndDate(months: String, date: String): String =
        s"The end date must be within $months months of the start date, you can't enter a date that's after $date"
    }

    object BusinessName {
      val empty = "Please enter a business name"
      val maxLength = "The business name is too long"
      val invalid = "The business name contains invalid characters"
    }

    object ContactEmail {
      val empty = "Please enter a contact email"
      val maxLength = "The email is too long"
      val invalid = "The email is invalid"
    }

    object IncomeType {
      val empty = "Please select an income type"
      val invalid = "Please select an income type"
    }

    object NotEligible {
      val empty = "Please select an option"
      val invalid = "Please select an option"
    }

    object Terms {
      val empty = "Please accept the terms and conditions"
    }

  }

  object Eligible {
    val title = "You can send digital updates"
    val heading: String = title
    val line_1 = "Your answers mean you should be able to start sending HMRC digital updates after you sign up."
    val line_2 = "You just need to enter a few more details."
  }

  object Not_Eligible {
    val title = "You can't send digital updates yet"
    val heading: String = title
    val line_1 = "Your answers mean you're not eligible to send digital updates to HMRC right now."
    val line_2 = "You can still sign up for the service. HMRC are working to make digital updates available for all sole traders in the coming months. If you sign up now, you will be able to send digital updates to HMRC when you become eligible."
    val line_3 = "If you choose to sign up, we'll ask you a few questions about your income and contact details."
    val signUp = "I want to sign up"
    val signOut = "I don't want to sign up - sign me out"
    val question = "Choose what you would like to do next"
  }

}
