package com.github.ximera239.api.core

import spray.http.{HttpEntity, StatusCode}

/**
 * User: Evgeny Zhoga
 * Date: 23.12.14
 */
case class ErrorResponseException(responseStatus: StatusCode, response: Option[HttpEntity]) extends Exception
