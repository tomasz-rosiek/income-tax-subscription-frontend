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
    val continueToSignUp = "Continue to sign up"
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
    val dateOfBirth = "Date of birth"
    val goBack = "Go back"
  }

  object FrontPage {
    val title = "Sign up to report your income and expenses quarterly"
    val heading = title
    val subHeading_1 = "Sign up using Government Gateway"
    val subHeading_2 = "What happens after you've signed up"
    val bullet_1 = "using your accounting software to record your income and expenses"
    val bullet_2 = "sending details to us regularly from May 2017"
    val bullet_3 = "agreeing to go paperless"
    val bullet_4 = "a sole trader with income from one business"
    val bullet_5 = "you rent out a UK property"
    val bullet_6 = "a sole trader with income from one business and you rent out a UK property"
    val bullet_7 = "complete your 2016 to 2017 Self Assessment tax return and pay what you owe by 31 January 2018"
    val bullet_8 = "send your summary report for the 2017 to 2018 tax year by 31 January 2019"
    val bullet_9 = "use your business tax account from August 2017 to see your reports and what you might owe"
    val line_1 = "By signing up to HM Revenue and Customs secure service, you're helping to test a new way of working out your tax. You'll do this by:"
    val line_2 = "You can sign up if your current accounting period starts after 5 April 2017 and you're one of the following:"
    val line_3 = "If you have income other than those listed above, you won't be able to report it using this service yet."
    val line_4 = "You'll need to enter the user ID and password you got when you signed up to the Self Assessment online service."
    val line_5 = "You'll need to:"
    val line_6 = "You can:"
  }

  object PreferencesCallBack {
    val title = "Do you want to continue?"
    val heading: String = "You need to agree to go paperless"
    val legend: String = "To sign up for this service, you need to allow HM Revenue and Customs to send you electronic communications."
    val button: String = "Go back"
    val signOut: String = "Sign out"
  }

  object IncomeSource {
    val title = "Where does your income come from?"
    val heading: String = title
    val business = "Your sole trader business"
    val property = "Renting out a UK property"
    val both = "Your sole trader business and renting out a UK property"
    val other = "Other"
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
      val heading: String = "Did your current accounting period start before 6 April 2017?"
      val accordion = "What's an accounting period?"
      val accordion_line1 = "This is the period that your latest income and expense records cover for Self Assessment."
      val accordion_line2 = "Your start date is usually 6 April (the same as the tax year), unless you selected a different date when you registered for Self Assessment."
      val yes = "Yes"
      val no = "No"
    }

    object RegisterNextAccountingPeriod {
      val title = "You can't use software to report your income yet"
      val heading = title
      val line_1 = "To report your income for your current accounting period, you'll have to submit a Self Assessment tax return."
      val line_2 = "You can sign up now and use software to record your income and expenses, but you won't be able to submit a report until your next accounting period."
      val button = "Continue to sign up"
      val signOut = "Sign out"
    }

  }

  object BusinessStartDate {
    val title = "Business start date"
    val heading = "When did your business start trading?"
    val exampleStartDate = "For example, 1 4 2018"
  }

  object AccountingPeriod {
    val title = "Business accounting period"
    val heading_current = "What are the dates of your current accounting period?"
    val heading_next = "When is your next accounting period?"
    val heading_registration = "When is your current accounting period?"
    val heading_editMode = "What are the dates of your accounting period?"
    val line_1_current = "Your accounting period is usually 6 April to 5 April (the same as the tax year), unless you selected different dates when you registered for Self Assessment."
    val line_1_next = "Your accounting period is usually 12 months. For example, 1 May 2017 to 30 April 2018."
    val line_1_registration = "Your accounting period is usually 12 months. For example, 6 April 2017 to 5 April 2018"
    val exampleStartDate_current = "For example, 6 4 2017"
    val exampleEndDate_current = "For example, 5 4 2018"
    val exampleStartDate_next = "For example, 1 4 2018"
    val exampleEndDate_next = "For example, 31 3 2019"
    val exampleStartDate_registration = "For example, 6 4 2017"
    val exampleEndDate_registration = "For example, 5 4 2018"
  }

  object BusinessName {
    val title = "Business name"
    val heading: String = "What's the name of your business?"

    object SignUp {
      val line_1 = "This is the business name you used to register for Self Assessment. If your business doesn't have a name, enter your own name."
    }

    object Registration {
      val line_1 = "If your business doesn't have a name, enter your own name."
    }

  }

  object BusinessPhoneNumber {
    val title = "Business phone number"
    val heading: String = "What's your business telephone number?"
  }

  object BusinessAddress {

    object Lookup {
      val heading = "What's your business address?"
      val nameOrNimber = "House name or number"
      val submit = "Search address"
      val enterManually = "Enter UK address manually"
    }

    object Select {
      val title = "Choose an address"
      val heading = "Choose an address"
      val edit = "Edit address"

    }

    object Confirm {
      val heading = "What's your business address?"
      val change = "Change"
    }

    object Edit {
      val heading = "Enter your address"
      val addLine1 = "Address line 1"
      val addLine2 = "Address line 2"
      val addLine3 = "Address line 3"
    }

  }

  object AccountingMethod {
    val title = "Accounting method"
    val heading: String = "How do you record your income and expenses?"
    val accordion = "Show me an example"
    val accordion_line_1 = "You invoiced someone in March 2017 but didn't receive the money until May 2017. If you would tell HM Revenue and Customs you received this income in:"
    val accordion_bullet_1 = "May 2017, then you use 'cash basis' accounting"
    val accordion_bullet_2 = "March 2017, then you use 'accruals basis'"
    val cash = "Cash basis You record on the date you receive money or pay a bill. Many sole traders and small businesses use this method."
    val accruals = "Accruals basis You record on the date you send or receive an invoice, even if you don't receive or pay any money. This method is also called 'traditional accounting'."
  }

  object Terms {
    val title = "Terms of participation"
    val heading: String = title
    val line_1 = "By taking part in this trial, you agree to:"
    val line_2 = "These terms aren't contractual and you can stop taking part in the trial at any time."
    val bullet_1 = "use accounting software to record your income and expenses, then submit a report once every 3 months from the start of your accounting period"
    val bullet_2 = "allow HMRC to calculate your Income Tax estimate using the information from your reports"
    val bullet_3 = "send HMRC your summary report for the 2017 to 2018 tax year by 31 January 2019"
    val bullet_4 = "declare any other income sources and reliefs"
    val bullet_5 = "authorise any third party you use to act on your behalf, such as your accountant"
    val bullet_6 = "have responsibility for any information a third party gives to HMRC on your behalf"
    val bullet_7 = "let HMRC know if you start a new business or your current business stops trading"
    val bullet_8 = "contact HMRC if you can't continue to take part in this trial"
    val button = "Accept and continue"
  }

  object Summary {
    val title = "Check your answers"
    val heading: String = title
    val heading_hidden = "before signing up"
    val h2 = "You've told us"
    val income_source = "Where does your income come from?"

    object IncomeSource {
      val business = "Sole trader business"
      val property = "Property"
      val both = "Sole trader business and property"
    }

    val accounting_period_prior = "Did your current accounting period start before 1 April 2017?"
    val accounting_period = s"Your accounting period dates"
    val accounting_period_registration = s"When's your current accounting period?"
    val accounting_period_month: Int => String = (month: Int) => s"$month month period"
    val business_name = "What's the name of your business?"
    val business_phone_number = "What's your business telephone number?"
    val business_address = "What's your business address?"
    val business_start_date = "When did your business start trading?"
    val income_type = "What method do you use for your accounting?"
    val other_income = "Do you have any other sources of income?"

    object AccountingMethod {
      val cash = "Cash basis"
      val accruals = "Accruals basis"
    }

    val contact_email = "Do you want to receive electronic communications from HMRC?"
    val confirm_and_sign_up = "Confirm and sign up"
  }

  object Timeout {
    val title = "Your session has timed out"
    val heading = "Your session has timed out"
    val returnToHome = """To sign up for quarterly reporting, you'll have to sign in using your Government Gateway ID."""
  }

  object AlreadyEnrolled {
    val title = "You've already signed up for quarterly reporting"
    val heading = "You've already signed up for quarterly reporting"
  }

  object UserDetailsError {
    val title = "User match error"
    val heading = "We couldn't confirm your details"
    val line1 = "The details you've entered are not on our system."
  }

  object UserDetailsLockout {
    val title = "You've been locked out"
    val heading = "You've been locked out"

    def line1(testTime: String) = s"To sign up for quarterly reporting, you'll have to try again in $testTime."
  }

  object UserDetails {
    val title = "Enter your details"
    val heading = "Enter your details"
    val line1 = "We will attempt to match these details against information we currently hold."
    val field1 = "First name"
    val field2 = "Last name"
    val field3 = "National Insurance number"
    val field4 = "Date of birth"
    val formhint1_line1 = "For example, 'QQ 12 34 56 C'."
    val formhint2 = "For example, 10 12 1990"
  }

  object ConfirmUser {
    val title = "Confirm your details"
    val heading = "Check your answers"
    val heading_hidden = "before looking up your details"
    val h2 = "You've told us"
    val firstName = "First name"
    val lastName = "Last name"
    val nino = "National Insurance number"
    val dob = "Date of birth"
  }


  object Confirmation {
    val title = "Confirmation page"
    val heading = "Sign up complete"
    val banner_line1 = "Make a note of your Income Tax reference number:"
    val signOut = "Sign out"

    object whatHappensNext {
      val para1 = "If you forget your Government Gateway login details, we can use this reference number to help you retrieve them."
      val heading = "What happens next"
      val para2 = "You must use accounting software to record your income and expenses and send a report to HM Revenue and Customs at least every quarter."
      val para3 = "After you send a report you'll get an Income Tax estimate. You can view your estimate and report deadlines in your:"
      val bul1 = "accounting software"
      val bul2 = "business tax account"
      val para4 = "It may take a few hours for them all to appear."
    }

  }

  object AffinityGroup {
    val title = "You can't use this service"
    val heading = "You can't use this service"
    val line1 = "You can only use this service if you have an individual Government Gateway account."
    val line2 = """To sign up for quarterly reporting, you'll need to sign in using a different type of account."""
    object Agent {
      val linkId: String = "agent-service"
      val linkText = "use our agent service."
      val line1 = s"To sign up for quarterly reporting with these sign in details, you need to $linkText"
    }
  }

  object Error {

    object BackToPreferences {
      val empty = "You must select an option to continue"
    }

    object Business {

      object SoleTrader {
        val empty = "You must select an option to continue"
        val invalid = "You must select an option to continue"
      }

      object AccountingPeriodPrior {
        val empty = "You must select an option to continue"
        val invalid = "You must select an option to continue"
      }

      object RegisterNextAccountingPeriod {
        val empty = "You must select an option to continue"
        val invalid = "You must select an option to continue"
      }

    }

    object Property {

      object Income {
        val empty = "You must select an option to continue"
        val invalid = "You must select an option to continue"
      }

    }

    object Date {
      val empty = "You must enter a date"
      val invalid = "You must enter a valid date"
      val end_violation = "You must enter a date greater than the start date"
    }

    object BusinessAccountingPeriod {
      val minStartDate = "You can't enter a start date before 6 April 2017"
      val maxEndDate = "You must provide an end date that is not more than 24 months after your start date"
    }

    object BusinessName {
      val empty = "You must enter your Business name"
      val maxLength = "You can't enter more than 105 characters for your Business name"
      val invalid = "The business name contains invalid characters"
    }

    object BusinessPhoneNumber {
      val empty = "You must enter your business phone number"
      val maxLength = "You can't enter more than 24 characters for your business phone number"
      val invalid = "The business phone number contains invalid characters"
    }

    object ContactEmail {
      val empty = "Please enter a contact email"
      val maxLength = "The email is too long"
      val invalid = "The email is invalid"
    }

    object AccountingMethod {
      val empty = "You must select an option to continue"
      val invalid = "You must select an option to continue"
    }

    object NotEligible {
      val empty = "You must select an option to continue"
      val invalid = "You must select an option to continue"
    }

    object OtherIncome {
      val empty = "You must select an option to continue"
      val invalid = "You must select an option to continue"
    }

    object IncomeSource {
      val empty = "You must select an option to continue"
      val invalid = "You must select an option to continue"
    }

    object Terms {
      val empty = "You must accept the terms of participation to continue"
    }

    object ExitSurvey {
      val maxLength = "You can't enter more than 1200 characters for your feedback"
    }

  }

  object Eligible {
    val title = "You can send digital updates"
    val heading: String = title
    val line_1 = "Your answers mean you should be able to start sending HMRC digital updates after you sign up."
    val line_2 = "You just need to enter a few more details."
  }

  object NoSA {
    val title = "You can't use this service yet"
    val heading = title
    val linkText = "register for Self Assessment"
    val line1 = s"You need to $linkText before you can sign up for quarterly reporting."
  }

  object MainIncomeError {
    val title = "You can't sign up for quarterly reporting yet"
    val heading = "You can't sign up for quarterly reporting yet"
    val para1 = "At the moment, you can only sign up if you're one of the following:"
    val para2 = "You'll be able to send quarterly reports for other income later in the year."
    val bullet1 = "a sole trader with income from one business"
    val bullet2 = "someone who rents out a UK property"
    val bullet3 = "a sole trader with income from one business and you rent out a UK property"
  }

  object OtherIncomeError {
    val title = "You can only use software to report some of your income"
    val heading: String = title
    val para1 = "As this is a trial service, you can only use software to report income from:"
    val bullet1 = "your sole trader business"
    val bullet2 = "renting out UK property"
    val bullet3 = "your sole trader business and renting out UK property"
    val para2 = "You'll be able to use software to report your other income later in the year."
  }

  object OtherIncome {
    val title = "Do you have any other sources of income?"
    val heading = "Do you have any other sources of income?"
    val para1 = "This could include:"
    val bullet1 = "employment that isn't your sole trader business"
    val bullet2 = "UK pensions or annuities"
    val bullet3 = "taxable state benefits"
    val bullet4 = "employment or investments from outside the UK"
    val bullet5 = "capital gains"
    val yes = "Yes"
    val no = "No"
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

  object IvFailed {
    val title = "We're unable to confirm your identity"
    val heading = title
    val line_1 = "To help protect your data, you can only sign up to report your income and expenses quarterly once we've confirmed who you are."
    val hmrcLink = "HM Revenue and Customs (opens in new window)"
    val line_2 = s"If you can't confirm your identity and you have a query you can contact $hmrcLink to get help."
    val tryAgainLink = "Try to confirm your identity again."
  }

}
