package com.github.ximera239.api.service1.services

import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorRef
import shapeless.{HNil, ::}
import spray.routing.{Directive0, PathMatcher, Directives}
import spray.routing.PathMatchers.{Slash, Segment}

import scala.concurrent.ExecutionContext

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class FindService(method: Directive0, pm: PathMatcher[::[Long, HNil]], findActor: AtomicReference[ActorRef])
                 (implicit executionContext: ExecutionContext)
  extends Directives {

  val extractData =
    path(pm) &
      method

  val route = extractData {
    (uid) =>
      complete {
        "Ok"
      }
  }
}
