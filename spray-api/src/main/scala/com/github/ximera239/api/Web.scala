package com.github.ximera239.api

import akka.io.IO
import com.github.ximera239.api.core.{ActorsCore, Core}
import spray.can.Http

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait Web {
  this: Api with ActorsCore with Core =>

  IO(Http)(system) ! Http.Bind(rootService, "0.0.0.0", port = 8080)

}
