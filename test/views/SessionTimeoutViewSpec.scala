/*
 * Copyright 2016 HM Revenue & Customs
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

package views

import assets.MessageLookup
import org.jsoup.Jsoup
import play.api.i18n.Messages.Implicits._
import play.api.test.FakeRequest
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}
import play.api.Play.current

class SessionTimeoutViewSpec extends UnitSpec with WithFakeApplication {

  lazy val page = views.html.timeout.timeout()(FakeRequest(), applicationMessages)
  lazy val document = Jsoup.parse(page.body)

  "The Session timeout view" should {

    s"have the title '${MessageLookup.timeout.title}'" in {
      document.title() shouldBe MessageLookup.timeout.title
    }

    s"have the heading (H1) '${MessageLookup.timeout.heading}'" in {
      document.getElementsByTag("H1").text() shouldBe MessageLookup.timeout.heading
    }
  }
}