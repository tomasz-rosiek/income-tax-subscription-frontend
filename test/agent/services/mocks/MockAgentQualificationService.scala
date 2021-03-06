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

package agent.services.mocks

import agent.audit.mocks.MockAuditingService
import agent.services.{UnexpectedFailure, _}
import agent.utils.TestConstants
import agent.utils.TestConstants.{testARN, testNino}
import agent.utils.TestModels.testClientDetails

trait MockAgentQualificationService extends MockKeystoreService
  with MockClientRelationshipService
  with MockClientMatchingService
  with MockSubscriptionService
  with MockAuditingService {

  object TestAgentQualificationService extends AgentQualificationService(
    mockClientMatchingService,
    mockClientRelationshipService,
    mockSubscriptionService,
    MockKeystoreService,
    mockAuditingService
  )

  def setupOrchestrateAgentQualificationSuccess(arn: String = TestConstants.testARN, nino: String = TestConstants.testNino): Unit = {
    setupMockKeystore(fetchClientDetails = testClientDetails)
    mockClientMatchSuccess(testClientDetails)
    setupMockGetSubscriptionNotFound(testNino)
    preExistingRelationship(testARN, testNino)(isPreExistingRelationship = true)
  }

  def setupOrchestrateAgentQualificationFailure(expectedResult: UnqualifiedAgent): Unit = {
    expectedResult match {
      case NoClientDetails => setupMockKeystore(fetchClientDetails = None)
      case _ => setupMockKeystore(fetchClientDetails = testClientDetails)
    }

    expectedResult match {
      case NoClientMatched => mockClientMatchNotFound(testClientDetails)
      case _ => mockClientMatchSuccess(testClientDetails)
    }

    expectedResult match {
      case ClientAlreadySubscribed => setupMockGetSubscriptionFound(testNino)
      case UnexpectedFailure => setupMockGetSubscriptionFailure(testNino)
      case _ => setupMockGetSubscriptionNotFound(testNino)
    }

    expectedResult match {
      case NoClientRelationship => preExistingRelationship(testARN, testNino)(isPreExistingRelationship = false)
      case _ => preExistingRelationship(testARN, testNino)(isPreExistingRelationship = true)
    }
  }

}
