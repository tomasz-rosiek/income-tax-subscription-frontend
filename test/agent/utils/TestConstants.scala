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

import java.time.OffsetDateTime

import agent.common.Constants._
import agent.connectors.models.gg._
import agent.connectors.models.matching.LockedOut
import agent.connectors.models.subscription.{Both, SubscriptionFailureResponse, SubscriptionRequest, SubscriptionSuccess}
import agent.models.DateModel
import play.api.http.Status._
import uk.gov.hmrc.domain.Generator

object TestConstants {
  /*
  * this nino is a constant, if you need a fresh one use TestModels.newNino
  */
  lazy val testNino = new Generator().nextNino.nino
  lazy val testMTDID = new Generator().nextAtedUtr.utr
  //Not a valid MTDID, for test purposes only
  lazy val startDate = DateModel("05", "04", "2017")
  lazy val endDate = DateModel("04", "04", "2018")
  lazy val ggServiceName = mtdItsaEnrolmentName
  lazy val agentServiceName = agentServiceEnrolmentName
  lazy val testARN = new Generator().nextAtedUtr.utr //Not a valid ARN, for test purposes only

  lazy val knownFactsRequest = KnownFactsRequest(
    List(
      TypeValuePair(mtdItsaEnrolmentIdentifierKey, testMTDID),
      TypeValuePair(agentServiceIdentifierKey, testNino)
    )
  )
  val testSubmissionRequest = SubscriptionRequest(
    nino = TestConstants.testNino,
    incomeSource = Both,
    arn = TestConstants.testARN,
    accountingPeriodStart = Some(startDate),
    accountingPeriodEnd = Some(endDate),
    cashOrAccruals = Some("Cash"),
    tradingName = Some("ABC")
  )

  val testErrorMessage = "This is an error"
  val testException = new Exception


  val testSubscriptionSuccess = Right(SubscriptionSuccess(testMTDID))

  val testSubscriptionFailure = Left(SubscriptionFailureResponse(INTERNAL_SERVER_ERROR))

  val testKnownFactsSuccess = Right(KnownFactsSuccess)

  val testKnownFactsFailure = Left(KnownFactsFailure(testErrorMessage))

  lazy val testLockoutResponse = LockedOut(testARN, OffsetDateTime.now())

}
