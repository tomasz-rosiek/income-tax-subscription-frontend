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

import javax.inject.{Inject, Singleton}

import agent.audit.Logging
import core.config.AppConfig
import agent.connectors.models.matching.{ClientMatchFailureResponseModel, ClientMatchRequestModel, ClientMatchSuccessResponseModel}
import agent.models.agent.ClientDetailsModel
import core.connectors.RawResponseReads
import play.api.http.Status._
import play.api.libs.json.{JsError, JsSuccess}
import uk.gov.hmrc.http.{HeaderCarrier, HttpPost, HttpResponse, InternalServerException}
import core.utils.Implicits._
import uk.gov.hmrc.play.http.logging.MdcLoggingExecutionContext._

import scala.concurrent.Future

@Singleton
class AuthenticatorConnector @Inject()(appConfig: AppConfig,
                                       val http: HttpPost,
                                       logging: Logging) extends RawResponseReads {

  lazy val matchingEndpoint: String = appConfig.authenticatorUrl + "/authenticator/match"

  def matchClient(clientDetailsModel: ClientDetailsModel)(implicit hc: HeaderCarrier): Future[Option[String]] = {

    val request: ClientMatchRequestModel = clientDetailsModel

    lazy val logFailure = (status: Int, response: HttpResponse) => {
      logging.warn(s"AuthenticatorConnector.matchClient unexpected response from authenticator: status=$status, body=" + response.body)
      new InternalServerException(s"AuthenticatorConnector.matchClient unexpected response from authenticator: status=$status body=${response.body}")
    }

    http.POST(matchingEndpoint, request).flatMap {
      response =>
        response.status match {
          case OK =>
            logging.debug("AuthenticatorConnector.matchClient response received: " + response.body)
            ClientMatchSuccessResponseModel.format.reads(response.json).fold(
              invalid => logFailure(OK, response),
              valid => Some(valid.nino)
            )
          case UNAUTHORIZED => // this end point should always return UNAUTHORIZED when there is a failure
            response.json.validate[ClientMatchFailureResponseModel] match {
              case JsSuccess(value, _) =>
                value match {
                  case ClientMatchFailureResponseModel(ClientMatchFailureResponseModel.unexpectedError) =>
                    logFailure(UNAUTHORIZED, response)
                  case _ =>
                    logging.info(s"AuthenticatorConnector.matchClient not found: status=$UNAUTHORIZED, value=$value, body=" + response.body)
                    None
                }
              case JsError(errors) => // the response body does not match an expected not found scenario
                logFailure(UNAUTHORIZED, response)
            }
          case status =>
            logFailure(status, response)
        }
    }
  }

}
