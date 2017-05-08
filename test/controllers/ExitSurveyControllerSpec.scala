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

package controllers

import assets.MessageLookup
import org.jsoup.Jsoup
import play.api.http.Status
import play.api.mvc.{Action, AnyContent}
import play.api.test.FakeRequest
import play.api.test.Helpers._


class ExitSurveyControllerSpec extends ControllerBaseSpec {

  override val controllerName: String = "ExitSurveyController"

  override val authorisedRoutes: Map[String, Action[AnyContent]] = Map()

  object TestExitSurveyController extends ExitSurveyController(
    app,
    appConfig,
    messagesApi
  )

  "ExitSurveyController.show" should {
    lazy val result = TestExitSurveyController.show()(FakeRequest())
    lazy val document = Jsoup.parse(contentAsString(result))

    "return ok (200)" in {
      status(result) must be(Status.OK)
    }

    "return HTML" in {
      contentType(result) must be(Some("text/html"))
      charset(result) must be(Some("utf-8"))
    }

    s"have the title '${MessageLookup.ExitSurvey.title}'" in {
      document.title() must be(MessageLookup.ExitSurvey.title)
    }
  }

  "ExitSurveyController.submit" should {
    lazy val result = TestExitSurveyController.submit()(FakeRequest())

    "return SEE_OTHER (303)" in {
      status(result) must be(Status.SEE_OTHER)
    }

    s"redirect to '${controllers.routes.ThankYouController.show().url}'" in {
      redirectLocation(result) mustBe Some(controllers.routes.ThankYouController.show().url)
    }

  }

}
