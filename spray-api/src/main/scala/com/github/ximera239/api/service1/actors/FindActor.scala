package com.github.ximera239.api.service1.actors

import akka.actor.{Props, Actor}

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class FindActor extends Actor {
  override def receive: Receive = Actor.emptyBehavior
}

object FindActor {
  def props = Props(new FindActor)
}