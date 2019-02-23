package studio.Gulity.GA2.Engine.Object.UI.GUI

import akka.actor.ActorRef
import studio.Gulity.GA2.Engine.Data.Devices

object GA2Swing_Scala {

  def Test = Devices.NewAndroidDevFromADB("M3")._2 match {
    case Some(d: ActorRef) =>d
    case None => false
  }
}
