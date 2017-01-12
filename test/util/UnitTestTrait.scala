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

package util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}
import play.twirl.api.Html
import uk.gov.hmrc.play.http.HeaderCarrier

import scala.concurrent.{ExecutionContextExecutor, Future}


trait UnitTestTrait extends PlaySpec with OneServerPerSuite {

  implicit val executionContext: ExecutionContextExecutor = scala.concurrent.ExecutionContext.Implicits.global

  implicit val hc = HeaderCarrier()

  implicit def optionWrapperUtil[T, S <: T](data: S): Option[T] = Some(data)

  implicit def futureWrapperUtil[T](value: T): Future[T] = Future.successful(value)

  implicit def futureWrapperUtil[T](err: Throwable): Future[T] = Future.failed(err)

  implicit def futureOptionWrapperUtil[T](value: T): Future[Option[T]] = Future.successful(value)

  implicit class HtmlFormatUtil(html: Html) {
    def doc: Document = Jsoup.parse(html.body)
  }

}
