package com.github.ximera239.api.core

/**
 * User: Evgeny Zhoga
 * Date: 22.12.14
 */
trait ActorsCore {
  this: Core =>

  object Service1Actors {
    import com.github.ximera239.api.service1.actors._

    val create = system.actorOf(CreateActor.props, "service1-create")
    val update = system.actorOf(UpdateActor.props, "service1-update")
    val delete = system.actorOf(DeleteActor.props, "service1-delete")

    val get = system.actorOf(GetActor.props, "service1-get")
    val find = system.actorOf(FindActor.props, "service1-find")
  }
}
