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

package incometax.incomesource.forms

import core.forms.validation.ErrorMessageFactory
import core.forms.validation.utils.ConstraintUtil._
import core.forms.validation.utils.MappingUtil._
import incometax.incomesource.models.IncomeSourceModel
import incometax.subscription.models.IncomeSourceType
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Valid}

object IncomeSourceForm {

  val incomeSource = "incomeSource"
  val option_business = IncomeSourceType.business
  val option_property = IncomeSourceType.property
  val option_both = IncomeSourceType.both

  val sourceEmpty: Constraint[String] = constraint[String](
    source => {
      lazy val emptySource = ErrorMessageFactory.error("error.income_source.empty")
      if (source.isEmpty) emptySource else Valid
    }
  )

  val sourceInvalid: Constraint[String] = constraint[String](
    source => {
      lazy val invalidSource = ErrorMessageFactory.error("error.income_source.invalid")
      source match {
        case `option_business` | `option_property` | `option_both` => Valid
        case _ => invalidSource
      }
    }
  )

  val incomeSourceForm = Form(
    mapping(
      incomeSource -> oText.toText.verifying(sourceEmpty andThen sourceInvalid)
    )(IncomeSourceModel.apply)(IncomeSourceModel.unapply)
  )

}
