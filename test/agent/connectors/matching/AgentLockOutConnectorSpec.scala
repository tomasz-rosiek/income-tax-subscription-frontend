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

package agent.connectors.matching

import agent.connectors.mocks.TestAgentLockoutConnector
import agent.utils.TestConstants._
import core.utils.UnitTestTrait

class AgentLockOutConnectorSpec extends UnitTestTrait with TestAgentLockoutConnector{

  "AgentLockOutConnector" should {
    "have the correct url" in {
      TestAgentLockoutConnector.agentLockoutUrl(testARN) must endWith(s"/income-tax-subscription/client-matching/lock/$testARN")
    }
  }

}
