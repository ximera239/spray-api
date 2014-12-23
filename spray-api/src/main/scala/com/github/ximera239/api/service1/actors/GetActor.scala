package com.github.ximera239.api.service1.actors

import akka.actor.{Props, Actor}

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class GetActor extends Actor {
  override def receive: Receive = Actor.emptyBehavior
}

object GetActor {
  def props = Props(new GetActor)
}