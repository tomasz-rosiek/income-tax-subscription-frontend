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

package agent.services

import agent.connectors.models.matching.{LockoutStatusFailureResponse, NotLockedOut}
import org.scalatest.EitherValues
import org.scalatest.Matchers._
import play.api.test.Helpers._
import agent.services.mocks.TestAgentLockoutService
import agent.utils.TestConstants
import agent.utils.TestConstants._

class AgentLockOutServiceSpec extends TestAgentLockoutService with EitherValues {

  val testArn: String = TestConstants.testARN

  "AgentLockoutService.lockoutAgent" should {

    def call = await(TestAgentLockoutService.lockoutAgent(arn = testArn))

    "return the not locked out status" in {
      setupMockLockCreated(testArn)
      call.right.value shouldBe testLockoutResponse
    }

    "return the error if lock status fails on bad request" in {
      setupMockLockFailureResponse(testArn)
      call.left.value shouldBe LockoutStatusFailureResponse(BAD_REQUEST)
    }

    "return the error if locked out throws an exception" in {
      setupMockLockException(testArn)
      intercept[Exception](call) shouldBe testException
    }
  }

  "AgentLockoutService.getLockOutStatus" should {

    def call = await(TestAgentLockoutService.getLockoutStatus(arn = testArn))

    "return the not locked out status" in {
      setupMockNotLockedOut(testArn)
      call.right.value shouldBe NotLockedOut
    }

    "return the locked out status" in {
      setupMockLockedOut(testArn)
      call.right.value shouldBe testLockoutResponse
    }

    "return the error if lock status fails on bad request" in {
      setupMockLockStatusFailureResponse(testArn)
      call.left.value shouldBe LockoutStatusFailureResponse(BAD_REQUEST)
    }

    "return the error if locked out throws an exception" in {
      setupMockLockStatusException(testArn)
      intercept[Exception](call) shouldBe testException
    }
  }

}
