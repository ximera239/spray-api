package com.github.ximera239.api.core

import java.util.concurrent.atomic.AtomicReference

import akka.actor._

import scala.util.Random

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait ActorsCore {
  this: Core =>

  object Service1Actors {
    import com.github.ximera239.api.service1.actors._

    def createWorker(props: Props, name: String, reference: AtomicReference[ActorRef]) = {
      val a = system.actorOf(props, name)
      val watchDog = system.actorOf(ReferenceWatchdog.props(a, reference), name + "-watchdog")
      a
    }

    val create = new AtomicReference[ActorRef]
    val update = new AtomicReference[ActorRef]
    val delete = new AtomicReference[ActorRef]
    val get = new AtomicReference[ActorRef]
    val find = new AtomicReference[ActorRef]

    create.set(createWorker(CreateActor.props, "service1-create", create))
    update.set(createWorker(UpdateActor.props, "service1-update", update))
    delete.set(createWorker(DeleteActor.props, "service1-delete", delete))
    get.set(createWorker(GetActor.props, "service1-get", get))
    find.set(createWorker(FindActor.props, "service1-find", find))
  }

  private class ReferenceWatchdog(initial: ActorRef, objectReference: AtomicReference[ActorRef]) extends Actor {
    val messageId = Random.nextInt()
    val pathToCheck = initial.path
    context.watch(initial)

    override def receive: Receive = {
      case Terminated(ref) if ref.path == pathToCheck =>
        context.unwatch(ref)
        context.actorSelection(pathToCheck) ! Identify(messageId)
      case ActorIdentity(correlationId, refOpt) if correlationId == messageId && refOpt.isDefined  =>
        val ref = refOpt.get
        context.watch(ref)
        objectReference.set(ref)
    }
  }

  private object ReferenceWatchdog {
    def props(initial: ActorRef, objectReference: AtomicReference[ActorRef]) = Props(new ReferenceWatchdog(initial, objectReference))
  }
}
