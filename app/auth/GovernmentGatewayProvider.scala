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

package auth

import play.api.mvc._
import uk.gov.hmrc.play.frontend.auth.GovernmentGateway
import scala.concurrent.Future
import play.api.mvc.Results.Redirect

class GovernmentGatewayProvider(postSignInRedirectUrl: String, loginUrl: String) extends GovernmentGateway {
  override def handleSessionTimeout(implicit request: Request[_]): Future[FailureResult] = GovernmentGatewayProvider.handleSessionTimeout(request)
  override def continueURL: String = postSignInRedirectUrl
  override def loginURL: String = this.loginUrl
}

object GovernmentGatewayProvider {
  def handleSessionTimeout(implicit request: Request[_]): Future[Result] =
    Future.successful(Redirect(controllers.routes.SessionTimeoutController.timeout().url))
}
