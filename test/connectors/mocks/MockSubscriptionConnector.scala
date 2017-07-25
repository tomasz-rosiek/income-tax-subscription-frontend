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

package connectors.mocks

import config.AppConfig
import connectors.models.subscription._
import connectors.subscription.SubscriptionConnector
import forms.{AccountingPeriodPriorForm, IncomeSourceForm, OtherIncomeForm}
import models._
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.{times, verify}
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR, OK}
import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpResponse}
import utils.JsonUtils._
import utils.TestConstants

import scala.concurrent.Future

trait MockSubscriptionConnector extends MockHttp {

  object TestSubscriptionConnector extends SubscriptionConnector(
    app.injector.instanceOf[AppConfig],
    mockHttpPost,
    mockHttpGet
  )

  def setupMockSubscribe(request: Option[SubscriptionRequest] = None)(status: Int, response: JsValue): Unit =
    setupMockHttpPost(url = TestSubscriptionConnector.subscriptionUrl(""), request)(status, response)

  def setupMockGetSubscription(nino: Option[String] = None)(status: Int, response: JsValue): Unit =
    setupMockHttpGet(
      url = nino.fold(None: Option[String])(nino => TestSubscriptionConnector.subscriptionUrl(nino))
    )(status, response)

  def setupSubscribe(request: Option[SubscriptionRequest] = None) = (setupMockSubscribe(request) _).tupled

  def setupGetSubscription(nino: Option[String] = None) = (setupMockGetSubscription(nino) _).tupled

  def verifyGetSubscription(nino: Option[String] = None)(checkAccess: Int): Unit =
    verify(mockHttpGet, times(checkAccess)).GET[HttpResponse](
      nino.fold(ArgumentMatchers.any())(nino => ArgumentMatchers.startsWith(TestSubscriptionConnector.subscriptionUrl(nino))))(ArgumentMatchers.any(), ArgumentMatchers.any()
    )

  val testRequest = SubscriptionRequest(
    nino = TestConstants.testNino,
    incomeSource = Both,
    accountingPeriodStart = TestConstants.startDate,
    accountingPeriodEnd = TestConstants.endDate,
    cashOrAccruals = "Cash",
    tradingName = "ABC"
  )

  val testId = TestConstants.testMTDID
  val badRequestReason = "Bad request"
  val internalServerErrorReason = "Internal server error"

  val subscribeSuccess = (OK, SubscriptionSuccessResponse(testId): JsValue)
  val subscribeEmptyBody = (OK, Json.obj())
  val subscribeBadRequest = (BAD_REQUEST, SubscriptionFailureResponse(badRequestReason): JsValue)
  val subscribeInternalServerError = (INTERNAL_SERVER_ERROR, SubscriptionFailureResponse(internalServerErrorReason): JsValue)

  def verifySubscriptionHeader(header: (String, String)): Future[HttpResponse] = verify(mockHttpPost).POST(
    ArgumentMatchers.any[String],
    ArgumentMatchers.any[String],
    ArgumentMatchers.any[Seq[(String, String)]]
  )(
    ArgumentMatchers.any(),
    ArgumentMatchers.any(),
    matches[HeaderCarrier](_.headers.contains(header))
  )

}
