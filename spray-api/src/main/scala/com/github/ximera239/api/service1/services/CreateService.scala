package com.github.ximera239.api.service1.services

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{TimeoutException => JTimeoutException}

import akka.actor.ActorRef
import akka.util.Timeout
import com.github.ximera239.api.core.{ExceptionDuringCreation, TimeoutException, Util}
import com.github.ximera239.api.service1.actors.CreateActor.{Create, Created, NotCreated}
import com.github.ximera239.model.BusinessObject
import org.slf4j.LoggerFactory
import shapeless.{::, HNil}
import spray.routing._

import scala.concurrent.{Await, ExecutionContext}

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class CreateService(method: Directive0, pm: PathMatcher[::[Long, HNil]], createActor: AtomicReference[ActorRef])
                   (implicit executionContext: ExecutionContext)
  extends Directives {

  val log = LoggerFactory.getLogger(getClass)

  import akka.pattern.ask
  import com.github.ximera239.api.service1.marshallers.Marshalling._
  import com.github.ximera239.api.service1.services.CreateService._

import scala.concurrent.duration._

  implicit val timeout = Timeout(2.seconds)

  val extractData =
    path(pm) &
      method &
      optionalHeaderValue(Util.extractUserIdentity) &
      optionalHeaderValue(Util.extractApiVersion) &
      parameters(QueryParams.`filters` ?, QueryParams.`some-parameter` ?) &
      entity(as[BusinessObject])

  val route = {
    extractData { (uid,
                   userIdentity,
                   version,
                   filters,
                   someParameter,
                   businessObject) =>
      (validateData(businessObject) & validateSomeParam(someParameter)) {
        try {
          Await.result(createActor.get ? Create(businessObject), timeout.duration) match {
            case Created(createdBusinessObject) =>
              complete {
                createdBusinessObject
              }
            case NotCreated(reason) =>
              failWith(ExceptionDuringCreation(reason))
          }
        } catch {
          case e: JTimeoutException =>
            failWith(TimeoutException)
        }
      }
    }
  }
}

object CreateService {

  import spray.routing.Directives._

  object QueryParams {
    val `filters` = "filters"
    val `some-parameter` = "some-parameter"
  }

  def validateData(data: BusinessObject): Directive0 =
    if (true) pass
    else reject(MalformedRequestContentRejection("Malformed data"))

  def validateSomeParam(data: Option[String]): Directive0 =
    if (true) pass
    else reject(MalformedQueryParamRejection(QueryParams.`some-parameter`, "Malformed param"))
}
