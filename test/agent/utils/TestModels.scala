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

package agent.utils

import agent.forms._
import agent.models.agent._
import agent.models._
import play.api.libs.json.{JsValue, Json}
import _root_.agent.services.CacheConstants
import core.utils.Implicits
import uk.gov.hmrc.domain.Generator
import uk.gov.hmrc.http.cache.client.CacheMap


object TestModels extends Implicits {

  import CacheConstants._

  /*
   * this function returns a random nino each time it is called, if you need a constant nino use TestConstants.testNino
   */
  def newNino: String = new Generator().nextNino.nino

  val testStartDate = DateModel("01", "04", "2017")
  val testEndDate = DateModel("01", "04", "2018")
  val testAccountingPeriodPriorCurrent: AccountingPeriodPriorModel = AccountingPeriodPriorModel(AccountingPeriodPriorForm.option_no)
  val testAccountingPeriodPriorNext: AccountingPeriodPriorModel = AccountingPeriodPriorModel(AccountingPeriodPriorForm.option_yes)
  val testAccountingPeriod: AccountingPeriodModel =
    testAccountingPeriod(testStartDate, testEndDate)

  def testAccountingPeriod(startDate: DateModel = testStartDate,
                           endDate: DateModel = testEndDate): AccountingPeriodModel =
    AccountingPeriodModel(startDate, endDate)

  val testBusinessName = BusinessNameModel("test business")
  val testAccountingMethod = AccountingMethodModel(AccountingMethodForm.option_cash)
  val testTerms = true

  val emptyCacheMap = CacheMap("", Map())

  val testCacheMap: CacheMap =
    testCacheMap(
      clientDetailsModel = testClientDetails,
      matchedNino = testClientDetails.ninoInBackendFormat,
      incomeSource = testIncomeSourceBoth,
      otherIncome = testOtherIncomeNo,
      accountingPeriodPrior = testAccountingPeriodPriorCurrent,
      accountingPeriodDate = testAccountingPeriod,
      businessName = testBusinessName,
      accountingMethod = testAccountingMethod,
      terms = testTerms)

  def testCacheMapCustom(
                          clientDetailsModel: Option[ClientDetailsModel] = testClientDetails,
                          matchedNino: Option[String] = testClientDetails.ninoInBackendFormat,
                          incomeSource: Option[IncomeSourceModel] = testIncomeSourceBoth,
                          otherIncome: Option[OtherIncomeModel] = testOtherIncomeNo,
                          accountingPeriodPrior: Option[AccountingPeriodPriorModel] = testAccountingPeriodPriorCurrent,
                          accountingPeriodDate: Option[AccountingPeriodModel] = testAccountingPeriod,
                          businessName: Option[BusinessNameModel] = testBusinessName,
                          accountingMethod: Option[AccountingMethodModel] = testAccountingMethod,
                          terms: Option[Boolean] = testTerms): CacheMap =
    testCacheMap(
      clientDetailsModel = clientDetailsModel,
      matchedNino = matchedNino,
      incomeSource = incomeSource,
      otherIncome = otherIncome,
      accountingPeriodPrior = accountingPeriodPrior,
      accountingPeriodDate = accountingPeriodDate,
      businessName = businessName,
      accountingMethod = accountingMethod,
      terms = terms)

  def testCacheMap(clientDetailsModel: Option[ClientDetailsModel] = None,
                   matchedNino: Option[String] = None,
                   incomeSource: Option[IncomeSourceModel] = None,
                   otherIncome: Option[OtherIncomeModel] = None,
                   accountingPeriodPrior: Option[AccountingPeriodPriorModel] = None,
                   accountingPeriodDate: Option[AccountingPeriodModel] = None,
                   businessName: Option[BusinessNameModel] = None,
                   accountingMethod: Option[AccountingMethodModel] = None,
                   terms: Option[Boolean] = None): CacheMap = {
    val emptyMap = Map[String, JsValue]()
    val map: Map[String, JsValue] = Map[String, JsValue]() ++
      clientDetailsModel.fold(emptyMap)(model => Map(ClientDetails -> ClientDetailsModel.format.writes(model))) ++
      matchedNino.fold(emptyMap)(model => Map(MatchedNino -> Json.toJson(model))) ++
      incomeSource.fold(emptyMap)(model => Map(IncomeSource -> IncomeSourceModel.format.writes(model))) ++
      otherIncome.fold(emptyMap)(model => Map(OtherIncome -> OtherIncomeModel.format.writes(model))) ++
      accountingPeriodPrior.fold(emptyMap)(model => Map(AccountingPeriodPrior -> AccountingPeriodPriorModel.format.writes(model))) ++
      accountingPeriodDate.fold(emptyMap)(model => Map(AccountingPeriodDate -> AccountingPeriodModel.format.writes(model))) ++
      businessName.fold(emptyMap)(model => Map(BusinessName -> BusinessNameModel.format.writes(model))) ++
      accountingMethod.fold(emptyMap)(model => Map(AccountingMethod -> AccountingMethodModel.format.writes(model))) ++
      terms.fold(emptyMap)(model => Map(Terms -> Json.toJson(model)))
    CacheMap("", map)
  }

  lazy val testIncomeSourceBusiness = IncomeSourceModel(IncomeSourceForm.option_business)

  lazy val testIncomeSourceOther = IncomeSourceModel(IncomeSourceForm.option_other)

  lazy val testIncomeSourceProperty = IncomeSourceModel(IncomeSourceForm.option_property)

  lazy val testIncomeSourceBoth = IncomeSourceModel(IncomeSourceForm.option_both)

  lazy val testIsCurrentPeriod = AccountingPeriodPriorModel(AccountingPeriodPriorForm.option_no)

  lazy val testIsNextPeriod = AccountingPeriodPriorModel(AccountingPeriodPriorForm.option_yes)

  lazy val testOtherIncomeNo = OtherIncomeModel(OtherIncomeForm.option_no)

  lazy val testOtherIncomeYes = OtherIncomeModel(OtherIncomeForm.option_yes)

  // we don't verify date of birth since an incorrect one would not result in a match so it can be any date
  lazy val testClientDetails = ClientDetailsModel("Test", "User", TestConstants.testNino, testStartDate)

  val testSummaryData = SummaryModel(
    incomeSource = IncomeSourceModel(IncomeSourceForm.option_both),
    otherIncome = OtherIncomeModel(OtherIncomeForm.option_no),
    accountingPeriodPrior = AccountingPeriodPriorModel(AccountingPeriodPriorForm.option_no),
    accountingPeriod = AccountingPeriodModel(TestConstants.startDate, TestConstants.endDate),
    businessName = BusinessNameModel("ABC"),
    accountingMethod = AccountingMethodModel("Cash")
  )

}
