package studio.Gulity.GA2.Engine.Data

import akka.actor.Actor

import scala.collection.mutable

/**
  * Created by Tamamo254 on 2019/1/13.
  */
class Console extends Actor {
  private[Data] val Logs = new mutable.LinkedHashMap[Long, EventLog]

  //  def Log(Str: String): Unit = {
  //  }

  def NewLog(New: EventLog): Unit = {
    println(New)
    Logs.put(New.UNIXTS, New)
  }

  override def receive = {
    case newLog: EventLog => NewLog(newLog)
  }


}
