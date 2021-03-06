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

package agent.connectors.mocks

import agent.audit.Logging
import agent.connectors.AgentServicesConnector
import org.mockito.Mockito._
import play.api.libs.json.JsValue
import core.utils.MockTrait

import scala.concurrent.Future

trait TestAgentServicesConnector extends MockHttp {
  object TestAgentServicesConnector extends AgentServicesConnector(appConfig, mockHttpGet, mockHttpPut, app.injector.instanceOf[Logging])

  def mockIsPreExistingRelationship(arn: String, nino: String)(status: Int, response: Option[JsValue] = None): Unit =
    setupMockHttpGet(Some(TestAgentServicesConnector.agentClientURL(arn, nino)))(status, response)

  def mockCreateClientRelationship(arn: String, mtdid: String)(status: Int, response: Option[JsValue]): Unit =
    setupMockHttpPut(Some(TestAgentServicesConnector.createClientRelationshipURL(arn, mtdid)))(status, response)
}

trait MockAgentServicesConnector extends MockTrait {

  val mockAgentServicesConnector = mock[AgentServicesConnector]

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockAgentServicesConnector)
  }

  def preExistingRelationship(arn: String, nino: String)(isPreExistingRelationship: Boolean): Unit =
    when(mockAgentServicesConnector.isPreExistingRelationship(arn, nino)).thenReturn(Future.successful(isPreExistingRelationship))

  def preExistingRelationshipFailure(arn: String, nino: String)(failure: Throwable): Unit =
    when(mockAgentServicesConnector.isPreExistingRelationship(arn, nino)).thenReturn(Future.failed(failure))

  def createClientRelationship(arn: String, mtdid: String): Unit =
    when(mockAgentServicesConnector.createClientRelationship(arn, mtdid)).thenReturn(Future.successful(()))

  def createClientRelationshipFailure(arn: String, mtdid: String)(failure: Throwable): Unit =
    when(mockAgentServicesConnector.createClientRelationship(arn, mtdid)).thenReturn(Future.failed(failure))
}
