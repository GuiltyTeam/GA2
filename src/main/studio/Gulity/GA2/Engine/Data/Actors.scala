package studio.Gulity.GA2.Engine.Data

import akka.actor.{ActorRef, ActorSystem, Props}

/**
  * ReCreated by Tamamo254 on 2019/1/12.
  */
object Actors {
  val ActrSys = ActorSystem()
  //  val DataBaser: ActorRef = ActrSys.actorOf(Props[DataBaser],"DataBaser")
  val Logger: ActorRef = ActrSys.actorOf(Props[Logger])
  val Console = ActrSys.actorOf(Props[Console])
  val Alert = ActrSys.actorOf(Props[Alerter])

}
