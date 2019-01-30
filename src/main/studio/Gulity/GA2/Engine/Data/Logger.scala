package studio.Gulity.GA2.Engine.Data

import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.Device

/**
  * Created by Tamamo254 on 2019/1/13.
  */

class Logger extends Actor {
  Logger.OtptWriter.append("\n\n\n=======\n")
  NewLog(new EventLog("程序启动，Logger激活"))

  override def receive: Receive = {
    case evt: EventLog if evt.EvtLevel.id >= Logger.LogLevel.id => NewLog(evt)
  }

  private def NewLog(NewLine: EventLog) = {
    Logger.OtptWriter.append("\n" + NewLine)
    Logger.OtptWriter.flush()
    Actors.Console ! NewLine
    Actors.Alert ! NewLine.Level
    if (NewLine.EvtParent.isDefined && NewLine.EvtParent.get.GUIPnl.isDefined) {
      NewLine.EvtParent.get.GUIPnl.get.UpdateConsole(NewLine.toString)
    }
  }
}

object Logger {
  val Path = "log/GA2.log"
  private val OtptWriter = new FileWriter(Path, true)
  val DateSDF = new SimpleDateFormat("yy.MM.dd HH:mm:ss")
  var LogLevel: EventLevel = EventLevel.Normal

  object EventLevel extends Enumeration {
    type EventLevel = Value
    val Debug = Value(0, "调试")
    val Normal = Value(1, "回报")
    val Warning = Value(2, "警告")
    val Error = Value(3, "错误")
    val Fatal = Value(4, "崩溃")
  }

}

class EventLog(val EvtTime: Date, val EvtParent: Option[Device], val EvtLevel: EventLevel, val LogContext: String) {
  def this(EventParent: Option[Device], EventLevel: EventLevel, LogContext: String) {
    this(new Date, EventParent, EventLevel, LogContext)
  }

  def this(LogContext: String) {
    this(None, EventLevel.Normal, LogContext)
  }

  def Time = Logger.DateSDF.format(EvtTime)

  def UNIXTS = EvtTime.getTime

  def Parent = EvtParent

  def Level = EvtLevel

  def Context = LogContext

  override def toString: String = {
    var SB: StringBuilder = new StringBuilder("【")
    SB.append(Level).append("】").append(Time).append("：")
    if (Parent.nonEmpty) {
      SB.append("[" + Parent.get.DevName  + Parent.get.DevID + "]"+ ":")
    }
    SB.append(Context).toString
  }


}

