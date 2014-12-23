package com.github.ximera239.api.core

import spray.http.StatusCodes._
import spray.http._
import spray.routing._
import spray.util.LoggingContext
import akka.actor.{Props, ActorLogging, Actor}
import scala.util.control.NonFatal

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class RoutedHttpService(route: Route) extends Actor with HttpService with ActorLogging {
  implicit def actorRefFactory = context
  implicit val handler = ExceptionHandler {
    case e: Errors => ctx =>
      e match {
        case TimeoutException => log.error("timeout")
        case ExceptionDuringCreation(reason) => log.error(e, reason)
      }

      ctx.complete(InternalServerError)
    case NonFatal(ErrorResponseException(statusCode, entity)) => ctx =>
      ctx.complete((statusCode, entity))
    case NonFatal(e) => ctx => {
      log.error(e, InternalServerError.defaultMessage)
      ctx.complete(InternalServerError)
    }
  }
  def receive: Receive =
    runRoute(route)(handler, RejectionHandler.Default, context, RoutingSettings.default, LoggingContext.fromActorRefFactory)

}

object RoutedHttpService {
  def props(route: Route) = Props(new RoutedHttpService(route))
}


