package com.github.ximera239.api.core

import spray.http.HttpHeader

/**
 * User: Evgeny Zhoga
 * Date: 23.12.14
 */
object Util {
  val `User-Identity` = "User-Identity"

  def extractUserIdentity: HttpHeader => Option[String] = {
    case h if h.name == `User-Identity` => Option(h.value).filter(_ != "")
    case _ => None
  }
  val `Api-Version` = "Api-Version"

  def extractApiVersion: HttpHeader => Option[String] = {
    case h if h.name == `Api-Version` => Option(h.value).filter(_ != "")
    case _ => None
  }
}
