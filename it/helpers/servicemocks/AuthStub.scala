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

package helpers.servicemocks

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import core.Constants._
import helpers.IntegrationTestConstants._
import play.api.http.Status._
import play.api.libs.json.{JsObject, Json}
import uk.gov.hmrc.auth.core.AffinityGroup

object AuthStub extends WireMockMethods {
  def stubAuthOrgAffinity(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Organisation, ninoEnrolment))
  }

  def stubAuthOrgAffinityNoEnrolments(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Organisation))
  }

  private val authoriseUri = "/auth/authorise"

  def stubAuthSuccess(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Individual, ninoEnrolment, utrEnrolment))
  }

  def stubAuthNoNino(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Individual, utrEnrolment))
  }

  def stubAuthNoUtr(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Individual, ninoEnrolment))
  }

  def stubEnrolled(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = OK, body = successfulAuthResponse(AffinityGroup.Individual, ninoEnrolment, utrEnrolment, mtdidEnrolment))
  }

  def stubUnauthorised(): StubMapping = {
    when(method = POST, uri = authoriseUri)
      .thenReturn(status = UNAUTHORIZED)
  }

  private val ninoEnrolment = Json.obj(
    "key" -> ninoEnrolmentName,
    "identifiers" -> Json.arr(
      Json.obj(
        "key" -> ninoEnrolmentIdentifierKey,
        "value" -> testNino
      )
    ),
    "confidenceLevel" -> 200
  )

  private val mtdidEnrolment = Json.obj(
    "key" -> mtdItsaEnrolmentName,
    "identifiers" -> Json.arr(
      Json.obj(
        "key" -> mtdItsaEnrolmentIdentifierKey,
        "value" -> testMTDID
      )
    ),
    "confidenceLevel" -> 200
  )

  private val utrEnrolment = Json.obj(
    "key" -> utrEnrolmentName,
    "identifiers" -> Json.arr(
      Json.obj(
        "key" -> utrEnrolmentIdentifierKey,
        "value" -> testUtr
      )
    ),
    "confidenceLevel" -> 200
  )

  private def successfulAuthResponse(affinityGroup: AffinityGroup, enrolments: JsObject*): JsObject =
  //Written out manually as the json writer for Enrolment doesn't match the reader
    Json.obj(
      "allEnrolments" -> enrolments,
      "affinityGroup" -> affinityGroup
    )
}
