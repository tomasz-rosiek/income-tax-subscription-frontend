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

package agent.assets

object MessageLookup {

  object Base {
    val continue = "Continue"
    val submit = "Submit"
    val update = "Update"
    val signOut = "Sign out"
    val startDate = "Start date"
    val endDate = "End date"
    val day = "Day"
    val month = "Month"
    val year = "Year"
    val errorHeading = "There's a problem"
    val change = "Change"
    val where_can_i_get_this_information = "Where can I get this information"
    val signUp = "Sign up"
    val startNow = "Start now"
    val dateOfBirth = "Date of birth"
    val goBack = "Go back"
  }

  object FrontPage {
    val title = "Sign up to report your client's income and expenses quarterly"
    val heading = title
    val subHeading_1 = "Sign up using Government Gateway"
    val subHeading_2 = "Before you start"

    val linkText_1 = "create an Agent Services account"
    val linkText_2 = "authorise you as their agent"

    val bullet_1 = "using your accounting software to record your client's income and expenses"
    val bullet_2 = "sending details to us regularly from July 2017"
    val bullet_3 = "a sole trader with income from one business"
    val bullet_4 = "someone who rents out a UK property"
    val bullet_5 = "a sole trader with income from one business and they rent out a UK property"
    val bullet_6 = "full name"
    val bullet_7 = "date of birth"
    val bullet_8 = "National Insurance Number"
    val bullet_9 = s"you need to $linkText_1"
    val bullet_10 = s"your client needs to $linkText_2"

    val para_1 = "By signing up to HM Revenue and Customs secure service, you're helping to test a new way of working out your client's tax. You'll do this by:"
    val para_2 = "You can sign up your client if their current accounting period starts after 5 April 2017 and they're one of the following:"
    val para_3 = "If your client has any income other than those listed above, you won't be able to report it using this service yet."
    val para_4 = "You'll need to enter the user ID and password you got when you created your Agent Services account."
    val para_5 = "You'll need your client's:"
    val para_6 = "To use this service:"
  }

  object PreferencesCallBack {
    val title = "Do you want to continue?"
    val heading: String = "To sign up, you must allow HMRC to send you electronic communications"
    val legend: String = "To sign up for quarterly reporting, you must allow HMRC to send you electronic communications"
    val yes: String = "Continue to sign up"
    val no: String = "Sign out"
  }

  object IncomeSource {
    val title = "Select your client's income type"
    val heading: String = title
    val business = "Sole trader business"
    val property = "Renting out a UK property"
    val both = "Their sole trader business and renting out a UK property"
    val other = "Other income sources"
  }

  object Property {

    object Income {
      val title = "How much was your income from property this year?"
      val heading: String = title
      val lt10k = "Less than £10,000"
      val ge10k = "£10,000 or more"
    }

  }

  object Business {

    object SoleTrader {
      val title = "Are you a sole trader?"
      val heading: String = title
      val line_1 = "As a sole trader, you run your own business as an individual. You can keep all your business's profits after you've paid tax on them. 'Sole trader' means you're responsible for the business, not that you have to work alone."
      val yes = "Yes - I'm a sole trader"
      val no = "No - I am a different type of business"
    }

    object AccountingPeriodPrior {
      val title = "Business accounting period"
      val heading: String = "Did your client's current accounting period start before 6 April 2017?"
      val yes = "Yes"
      val no = "No"
    }

    object RegisterNextAccountingPeriod {
      val title = "You can't send quarterly reports yet"
      val heading = title
      val line_1 = "You can sign your client up now, but you won't be able to send any reports until their next accounting period begins."
      val button = "Continue to sign up"
    }

  }

  object AccountingPeriod {
    val title = "Business accounting period"
    val heading_current = "Enter your client's current accounting period dates"
    val heading_next = "Enter your client's next accounting period dates"
    val heading_editMode = "What are the dates of your accounting period?"
    val exampleStartDate_current = "For example, 6 4 2017"
    val exampleEndDate_current = "For example, 5 4 2018"
    val exampleStartDate_next = "For example, 1 4 2018"
    val exampleEndDate_next = "For example, 31 3 2019"
  }

  object ContactEmail {
    val title = "Enter your email address"
    val heading: String = title
    val line_1 = "We'll use this to get in touch with updates about your estimated Income Tax calculations."
  }

  object BusinessName {
    val title = "Business name"
    val heading: String = "What's the name of your client's business?"
    val line_1 = "This is the business name they used to register for Self Assessment. If their business doesn't have a name, enter your client's name."
  }

  object AccountingMethod {
    val title = "Accounting method"
    val heading: String = "What accounting method does your client use?"
    val cash = "Cash basis"
    val accruals = "Traditional accounting"
  }

  object Terms {
    val title = "Terms of participation"
    val heading: String = title
    val line_1 = "By signing your client up to take part in this trial, you agree that either you or your client will:"
    val line_2 = "These terms aren't contractual and your client can stop taking part in the trial at any time."
    val point_1 = "use accounting software to record your client's income and expenses"
    val point_2 = "submit a report once every 3 months from the start of your client's accounting period"
    val point_3 = "allow HMRC to calculate your client's Income Tax estimate using the information from their reports"
    val point_4 = "send HMRC your client's summary report for the 2017 to 2018 tax year by 31 January 2019"
    val point_5 = "declare any of your client's other income sources and reliefs"
    val point_6 = "let HMRC know if your client starts a new business or their current business stops trading"
    val point_7 = "contact HMRC if your client can't continue to take part in this trial or you stop representing this client"
    val button = "Accept and continue"
  }

  object Summary {
    val title = "Check your answers"
    val heading: String = title
    val heading_hidden = "before signing up"
    val h2 = "You've told us"
    val income_source = "Your client's income type"

    object IncomeSource {
      val business = "Sole trader business"
      val property = "Property"
      val both = "Sole trader business and property"
    }

    val accounting_period_prior = "Did your current accounting period start before 1 April 2017?"
    val accounting_period = s"Your client's accounting period dates"
    val accounting_period_month: Int => String = (month: Int) => s"$month month period"
    val business_name = "What's the name of your client's business?"
    val income_type = "What accounting method does your client use?"
    val other_income = "Does your client have any other sources of income?"

    object AccountingMethod {
      val cash = "Cash basis"
      val accruals = "Traditional accounting"
    }

    val contact_email = "Do you want to receive electronic communications from HMRC?"
    val terms = "Terms of participation"
    val terms_agreed = "I agree"
    val confirm_and_sign_up = "Confirm and sign up"
  }

  object Timeout {
    val title = "Your session has timed out"
    val heading = "Your session has timed out"
    val returnToHome = """To sign your client up for quarterly reporting, you'll have to sign in using your Government Gateway ID."""
  }

  object ClientAlreadySubscribed {
    val title = "Your client has already signed up"
    val heading = "Your client has already signed up"
    val para1 = "The details you've entered are already in use."
  }

  object NoClientRelationship {
    val title = "You're not authorised for this client"
    val heading = "You're not authorised for this client"
    val para1 = "To send quarterly reports, your client must authorise you as their agent."
  }

  object Confirmation {
    val title = "Confirmation page"
    val heading = "Sign up complete"
    val banner_line1_1 = "Make a note of your client's reference number:"
    val addAnother = "Sign up another client"

    object whatHappensNext {
      val para1 = "If your client forgets their Government Gateway login details, we can use this reference number to help retrieve them."
      val heading = "What happens next"
      val para2 = "You must use accounting software to keep your client's accounts, and send a report to HMRC at least every quarter."
      val para3 = "You'll get an estimate of your client's Income Tax in your software when you send a report. From August 2017 your client can view these Income Tax calculations and their report deadlines in their business tax account."
    }

    object giveUsFeedback {
      val heading = "Give us feedback"
      val para1 = "Your feedback helps us improve."
      val link = "What did you think of this service?"
    }

  }

  object Error {

    object BackToPreferences {
      val empty = "You must select an option to continue"
    }

    object Business {

      object AccountingPeriodPrior {
        val empty = "Select yes if their accounting period started before 6 April 2017"
        val invalid = "Select yes if their accounting period started before 6 April 2017"
      }

    }


    object StartDate {
      val empty = "Enter a start date"
      val invalid_chars = "Enter a start date using numbers 0 to 9"
      val invalid = "Enter a real start date"
    }

    object EndDate {
      val empty = "Enter an end date"
      val invalid_chars = "Enter an end date using numbers 0 to 9"
      val invalid = "Enter a real end date"
      val end_violation = "Enter an end date that's after the start date"
    }

    object DOBDate {
      val empty = "Enter a date of birth"
      val invalid_chars = "Enter a date of birth using numbers 0 to 9"
      val invalid = "Enter a real date of birth"
    }


    object BusinessAccountingPeriod {
      val minStartDate = "Enter a start date after 5 April 2017"
      val maxEndDate = "Enter an end date that's less than 24 months after your start date"
    }

    object BusinessName {
      val empty = "Enter your client's business name"
      val maxLength = "Enter a business name that's 105 characters or less"
      val invalid = "Enter a valid business name"
    }

    object ContactEmail {
      val empty = "Please enter a contact email"
      val maxLength = "The email is too long"
      val invalid = "The email is invalid"
    }

    object AccountingMethod {
      val empty = "Select an accounting method"
      val invalid = "Select an accounting method"
    }

    object NotEligible {
      val empty = "You must select an option to continue"
      val invalid = "You must select an option to continue"
    }

    object OtherIncome {
      val empty = "Select yes if your client has other income sources"
      val invalid = "Select yes if your client has other income sources"
    }

    object IncomeSource {
      val empty = "Select an income type"
      val invalid = "Select an income type"
    }

    object Terms {
      val empty = "You must accept the terms of participation to continue"
    }

    object ClientDetails {
      val firstNameEmpty = "Enter your client's first name"
      val lastNameEmpty = "Enter your client's last name"
      val firstNameInvalid = "Enter a valid first name"
      val lastNameInvalid = "Enter a valid last name"
      val firstNameMaxLength = "Enter a first name that's 105 characters or less"
      val lastNameMaxLength = "Enter a last name that's 105 characters or less"
    }

    object Nino {
      val empty = "Enter your client's National Insurance number"
      val invalid = "Enter a valid National Insurance number"
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

  object NoNino {
    val title = "You can't sign up for quarterly reporting yet"
    val heading = title
    val line1 = "You can sign up later in the year when this service is available to you."
  }

  object MainIncomeError {
    val title = "You can't sign up your client yet"
    val heading = "You can't sign up your client yet"
    val para1 = "At the moment, you can only sign up if your client is one of the following:"
    val para2 = "You'll be able to use this reporting method for your client later in the year."
    val bullet1 = "a sole trader with income from one business"
    val bullet2 = "someone who rents out a UK property"
    val bullet3 = "a sole trader with income from one business and they rent out a UK property"
  }

  object OtherIncomeError {
    val title = "You can only send quarterly reports on part of your client's income"
    val heading: String = title
    val para1 = "You'll be able to use this reporting method for your client's other income later in the year."
  }

  object OtherIncome {
    val title = "Does your client have any other sources of income?"
    val heading = "Does your client have any other sources of income?"
    val para1 = "This could include:"
    val bullet1Default = "employment that isn't their sole trader business"
    val bullet1Property = "employment"
    val bullet2 = "UK pensions or annuities"
    val bullet3 = "taxable state benefits"
    val bullet4 = "employment or investments from outside the UK"
    val bullet5 = "capital gains"
    val yes = "Yes"
    val no = "No"
  }


  object NotEnrolledAgentServices {
    val title = "You can't sign up your clients yet"
    val heading = title
    val linkText = "create an Agent Services account"
    val para1 = s"To sign up for quarterly reporting, you must $linkText."
  }

  object ClientDetailsError {
    val title = "Client match error"
    val heading = "We couldn't confirm your client"
    val line1 = "The details you've entered are not on our system."
  }

  object ClientDetailsLockout {
    val title = "You've been locked out"
    val heading = "You've been locked out"

    def line1(testTime: String) = s"To sign your client up for quarterly reporting, you'll have to try again in $testTime."
  }

  object ClientDetails {
    val title = "Enter your client's details"
    val heading = "Enter your client's details"
    val line1 = "We will attempt to match these details against information we currently hold."
    val field1 = "First name"
    val field2 = "Last name"
    val field3 = "National Insurance number"
    val field4 = "Date of birth"
    val formhint1_line1 = "For example, 'QQ 12 34 56 C'."
    val formhint2 = "For example, 10 12 1990"
  }

  object ConfirmClient {
    val title = "Confirm your client"
    val heading = "Check your answers"
    val heading_hidden = "before looking up your client"
    val h2 = "You've told us"
    val firstName = "First name"
    val lastName = "Last name"
    val nino = "National Insurance number"
    val dob = "Date of birth"
  }

  object ExitSurvey {
    val title = "Give feedback"
    val heading = "Give feedback"
    val line_1 = "Please don't include any personal or financial information, for example your National Insurance or credit card numbers."
    val submit = "Send feedback"

    object Q1 {
      val question = "Overall, how did you feel about the service you received today?"
      val option_1 = "Very satisfied"
      val option_2 = "Satisfied"
      val option_3 = "Neither satisfied or dissatisfied"
      val option_4 = "Dissatisfied"
      val option_5 = "Very dissatisfied"
    }

    object Q2 {
      val question = "How could we improve this service?"
    }

  }


  object ThankYou {
    val title = "Thank you"
    val heading = "Thank you"
    val line_1 = "Your feedback will help us improve this service."
    val gotoGovUk = "Go to the GOV.UK page"
  }

}
