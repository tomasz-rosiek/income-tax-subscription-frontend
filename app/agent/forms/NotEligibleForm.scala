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

package agent.forms

import forms.validation.ErrorMessageFactory
import forms.validation.utils.ConstraintUtil._
import forms.validation.utils.MappingUtil._
import models.NotEligibleModel
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.validation.{Constraint, Valid}


object NotEligibleForm {
  val choice = "choice"
  val option_signup = "SignUp"
  val option_signout = "SignOut"

  val choiceEmpty: Constraint[String] = constraint[String](
    income => {
      lazy val emptyIncome = ErrorMessageFactory.error("error.not-eligible.empty")
      if (income.isEmpty) emptyIncome else Valid
    }
  )

  val choiceInvalid: Constraint[String] = constraint[String](
    income => {
      lazy val invalidName = ErrorMessageFactory.error("error.not-eligible.invalid")
      income match {
        case `option_signup` | `option_signout` => Valid
        case _ => invalidName
      }
    }
  )

  val notEligibleForm = Form(
    mapping(
      choice -> oText.toText.verifying(choiceEmpty andThen choiceInvalid)
    )(NotEligibleModel.apply)(NotEligibleModel.unapply)
  )
}
