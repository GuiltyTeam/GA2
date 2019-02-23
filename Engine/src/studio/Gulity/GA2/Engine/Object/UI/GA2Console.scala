package studio.Gulity.GA2.Engine.Object.UI

import java.util.Scanner

import akka.actor.ActorRef
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Data.{Actors, Devices, EventLog}
import studio.Gulity.GA2.Engine.GA2System


import scala.util.control.Breaks._

abstract class GA2Console extends App {
  val Scnr = new Scanner(System.in)
  var CntDev: Option[ActorRef] = None
  var Input: String = ""
  val Path: Array[String] = Array("", "")

  GA2System.Initialization
  AfterInit
  //  Actors.ActrSys.eventStream.setLogLevel(Logging.InfoLevel)
  //  Devices.ActrSys_Devices.eventStream.setLogLevel(Logging.InfoLevel)

  print(args.length)

  Thread.sleep(1000)
  if (args.length >= 2) {
    Log(EventLevel.Normal, "自动打开设备：" + args(0) + "，直接运行脚本：" + args(1))
    if (!(args(0).isEmpty || args(1).isEmpty)) Cnt2Dev(args(0), args(1))
  }

  breakable {
    while (true) {
      PrintHead
      Input = Scnr.nextLine.trim
      if (Input == "Exit") break()
      if (!ExtndCmdInGA2(Input)) {
        Input.split(":")(0) match {
          case str: String if str == "ADB" || str == "NewDevFromADB" =>
            Cnt2Dev(Input.split(":")(1))
          case "Help" => PrientHelper
          case _ => WTF
        }
      }
    }
  }
  GA2System.Shutdown
  println("Bye H.A.N.D")

  def InDev(Dev: ActorRef) = {
    CntDev = Some(Dev)
    breakable {
      while (true) {
        PrintHead
        Input = Scnr.nextLine
        if (Input == "Back" || Input == "DisCnt") break()
        if (!ExtndCmdInDev(Input)) {
          Input match {
            //          case str: String if str.matches("^Run:[-_a-zA-Z0-9:]+$") => InScpt(str.substring(4))
            case "Help" => PrientHelper
            case str: String => Dev ! str
          }
        }
      }
    }
    CntDev = None
    Path(0) = ""
  }

  def InDev(Dev: ActorRef, ScptName: String = "") = {
    CntDev = Some(Dev)
    breakable {
      PrintHead
      if (!ScptName.eq("")) CntDev.get ! "Run:" + ScptName
      while (true) {
        Input = Scnr.nextLine
        if (Input == "Back" || Input == "DisCnt") break()
        if (!ExtndCmdInDev(Input)) {
          Input match {
            //          case str: String if str.matches("^Run:[-_a-zA-Z0-9:]+$") => InScpt(str.substring(4))
            case "Help" => PrientHelper
            case str: String => Dev ! str
          }
        }
      }
    }
    CntDev = None
    Path(0) = ""
  }

  def PrintHead = {
    print("\n[")
    if (CntDev.isEmpty) {
      print("None")
    } else {
      print(Path(0))
    }
    print("]")
    if (!Path(1).isEmpty) print(" " + Path(1).substring(14) + " ")
    print(" -> ")

  }


  def PrientHelper = CntDev match {
    case None => println("抱歉偷懒了还没写")
    case Some(a: ActorRef) => print("抱歉偷懒了还没写")
  }

  def WTF = println("未定义的指令，小老弟你肿么回事 ヾ(。￣□￣)ﾂ゜゜゜")

  def Log(Level: EventLevel, Context: String) = {
    Actors.Logger ! new EventLog(None, Level, Context)
  }

  def Cnt2Dev(DevName: String, ScptName: String = "", IsRetry: Boolean = false): Unit = {
    var Rtn = Devices.NewAndroidDevFromADB(DevName)
    Rtn._2 match {
      case Some(d: ActorRef) =>
        Path(0) = DevName
        InDev(d, ScptName)
      case None if Rtn._1.eq("ShellCommandUnresponsiveException") && !IsRetry =>
        println("正在尝试重连")
        Cnt2Dev(DevName, ScptName, true)
      case _ =>
    }
  }


  def AfterInit: Unit

  def ExtndCmdInGA2(Cmd: String): Boolean

  def ExtndCmdInDev(Cmd: String): Boolean
}