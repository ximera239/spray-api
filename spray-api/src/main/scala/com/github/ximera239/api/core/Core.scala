package com.github.ximera239.api.core

import akka.actor.ActorSystem

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait Core {
  protected implicit def system: ActorSystem
}
