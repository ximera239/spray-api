package com.github.ximera239.api

import akka.actor.Props
import com.github.ximera239.api.core.{ActorsCore, Core, RoutedHttpService}
import spray.routing.{Directives, RouteConcatenation}

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait Api extends RouteConcatenation with Directives {
  this: ActorsCore with Core  =>

  private implicit val _ = system.dispatcher

  val routes =
    new service1.services.CreateService(post, "api" / "service1" / "user" / LongNumber / "objects" ~ Slash.?, Service1Actors.create).route ~
      new service1.services.UpdateService(put, "api" / "service1" / "user" / LongNumber / "object" / Segment ~ Slash.?, Service1Actors.update).route ~
      new service1.services.DeleteService(delete, "api" / "service1" / "user" / LongNumber / "objects" / Segment ~ Slash.?, Service1Actors.delete).route ~
      new service1.services.GetService(get, "api" / "service1" / "user" / LongNumber / "objects" / Segment ~ Slash.?, Service1Actors.get).route ~
      new service1.services.FindService(get, "api" / "service1" / "user" / LongNumber / "objects" ~ Slash.?, Service1Actors.find).route

  val rootService = system.actorOf(RoutedHttpService.props(routes))
}
