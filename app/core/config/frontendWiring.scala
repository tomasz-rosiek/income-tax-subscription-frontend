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

package core.config

import javax.inject._

import com.typesafe.config.Config
import play.api.Application
import uk.gov.hmrc.auth.core.PlayAuthConnector
import uk.gov.hmrc.play.audit.http.connector.{AuditConnector => Auditing}
import uk.gov.hmrc.play.config.{AppName, RunMode, ServicesConfig}
import uk.gov.hmrc.play.frontend.filters.SessionCookieCryptoFilter
import uk.gov.hmrc.play.partials.HeaderCarrierForPartialsConverter
import uk.gov.hmrc.http._
import uk.gov.hmrc.play.frontend.config.LoadAuditingConfig
import uk.gov.hmrc.play.http.ws._

import scala.concurrent.Future

@Singleton
class FrontendAuditConnector @Inject()(val app: Application) extends Auditing with AppName {

  override lazy val auditingConfig = LoadAuditingConfig(s"auditing")
}

@Singleton
class WSHttp @Inject()(val app: Application)
  extends uk.gov.hmrc.play.http.ws.WSHttp
    with HttpGet with HttpPost with HttpPut with HttpDelete with HttpPatch
    with AppName with RunMode {
  override val hooks = NoneRequired
}

@Singleton
class SessionCache @Inject()(val app: Application,
                             val http: WSHttp) extends uk.gov.hmrc.http.cache.client.SessionCache with AppName with ServicesConfig {
  lazy val defaultSource: String = getConfString("session-cache.income-tax-subscription-frontend.cache", "income-tax-subscription-frontend")

  lazy val baseUri = baseUrl("session-cache")
  lazy val domain = getConfString("session-cache.domain", throw new Exception(s"Could not find core.config 'session-cache.domain'"))
}

trait SessionCookieCryptoFilterWrapper {

  def encryptCookieString(cookie: String): String = {
    SessionCookieCryptoFilter.encrypt(cookie)
  }
}

object ITSAHeaderCarrierForPartialsConverter extends HeaderCarrierForPartialsConverter with SessionCookieCryptoFilterWrapper {
  override val crypto = encryptCookieString _
}

@Singleton
class AuthConnector @Inject()(appConfig: AppConfig, val http: HttpPost) extends PlayAuthConnector {
  override lazy val serviceUrl: String = appConfig.authUrl
}

