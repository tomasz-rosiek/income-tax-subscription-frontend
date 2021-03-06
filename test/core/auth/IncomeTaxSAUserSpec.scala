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

package core.auth

import core.utils.TestConstants.{testNino, testUtr}
import core.{Constants, ITSASessionKeys}
import play.api.test.FakeRequest
import uk.gov.hmrc.auth.core.ConfidenceLevel.L50
import uk.gov.hmrc.auth.core.{ConfidenceLevel, Enrolment, EnrolmentIdentifier, Enrolments}
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class IncomeTaxSAUserSpec extends UnitSpec with WithFakeApplication {

  "IncomeTaxSAUser" when {
    "Nino and UTR are retrieved from auth" should {
      implicit lazy val request = FakeRequest()

      val confidenceLevel = L50

      lazy val user = IncomeTaxSAUser(
        Enrolments(
          Set(
            Enrolment(Constants.ninoEnrolmentName,
              Seq(EnrolmentIdentifier(Constants.ninoEnrolmentIdentifierKey, testNino)),
              "Activated",
              confidenceLevel
            ),
            Enrolment(Constants.utrEnrolmentName,
              Seq(EnrolmentIdentifier(Constants.utrEnrolmentIdentifierKey, testUtr)),
              "Activated",
              confidenceLevel
            )
          )
        ),
        None
      )

      s"have the expected NINO $testNino" in {
        user.nino shouldBe Some(testNino)
      }

      s"have the expected UTR $testUtr" in {
        user.utr shouldBe Some(testUtr)
      }

      s"have the confidence level of $confidenceLevel" in {
        user.confidenceLevel shouldBe confidenceLevel
      }
    }

    "Nino and UTR are stored in session after being pulled from CID" should {
      val confidenceLevel = ConfidenceLevel.L0

      implicit lazy val request = FakeRequest().withSession(
        ITSASessionKeys.NINO -> testNino,
        ITSASessionKeys.UTR -> testUtr
      )

      lazy val user = IncomeTaxSAUser(
        Enrolments(Set.empty),
        None
      )

      s"have the expected NINO $testNino" in {
        user.nino shouldBe Some(testNino)
      }

      s"have the expected UTR $testUtr" in {
        user.utr shouldBe Some(testUtr)
      }

      "have the default confidence level of 0" in {
        user.confidenceLevel shouldBe ConfidenceLevel.L0
      }
    }
  }
}
