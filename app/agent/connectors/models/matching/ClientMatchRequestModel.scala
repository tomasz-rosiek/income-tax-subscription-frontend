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

package agent.connectors.models.matching

import java.time.LocalDate

import agent.models.agent.ClientDetailsModel
import agent.models.DateModel._
import play.api.libs.json.Json
import uk.gov.hmrc.domain.Nino


case class ClientMatchRequestModel(firstName: String, lastName: String, dateOfBirth: LocalDate, nino: Nino)

object ClientMatchRequestModel {

  implicit def requestConvert(clientDetailsModel: ClientDetailsModel): ClientMatchRequestModel =
    ClientMatchRequestModel(
      clientDetailsModel.firstName,
      clientDetailsModel.lastName,
      clientDetailsModel.dateOfBirth,
      Nino(clientDetailsModel.ninoInBackendFormat)
    )

  implicit val format = Json.format[ClientMatchRequestModel]
}
