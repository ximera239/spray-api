package com.github.ximera239.api.service1.actors

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, Props}
import org.slf4j.LoggerFactory
import com.github.ximera239.model.BusinessObject

import scala.util.Random

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
class CreateActor extends Actor {

  import com.github.ximera239.api.service1.actors.CreateActor._

  override def receive: Receive = {
    case Create(businessObject) =>
      val i = Random.nextInt(4)
      log.info(s">>>>> Will sleep $i seconds")
      Thread.sleep(TimeUnit.SECONDS.toMillis(i))
      log.info(s">>>>> Object created [${businessObject.id}] with description [${businessObject.description}]")
      sender() ! Created(businessObject)
  }
}

object CreateActor {
  val log = LoggerFactory.getLogger(getClass)

  def props = Props(new CreateActor)

  case class Create(businessObject: BusinessObject)

  case class Created(businessObject: BusinessObject)

  case class NotCreated(reason: String)

}

