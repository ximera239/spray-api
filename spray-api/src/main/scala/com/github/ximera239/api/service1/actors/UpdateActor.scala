package com.github.ximera239.api.service1.actors

import akka.actor.{Actor, Props}

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class UpdateActor extends Actor {
  override def receive: Receive = Actor.emptyBehavior
}

object UpdateActor {
  def props = Props(new UpdateActor)
}