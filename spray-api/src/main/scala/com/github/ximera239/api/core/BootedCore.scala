package com.github.ximera239.api.core

import akka.actor.ActorSystem

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait BootedCore extends Core {
  implicit val system = ActorSystem("spray-ugc")

  sys.addShutdownHook(system.shutdown())
}
