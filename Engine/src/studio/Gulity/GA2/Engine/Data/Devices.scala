package studio.Gulity.GA2.Engine.Data


import akka.actor.{ActorRef, ActorSystem, Props}
import akka.japi.Option.Some
import com.android.chimpchat.adb.AdbBackend
import com.android.chimpchat.core.IChimpDevice
import studio.Gulity.GA2.Cfg.DevID
import studio.Gulity.GA2.Engine.Cfg.EngineSetting
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.Bind.Mobile.ADB.ADBDevice
import studio.Gulity.GA2.Engine.Object.UI.GUI.Component.GA2DevPnl

import scala.collection.mutable.LinkedHashMap

object Devices {

  val ActrSys_Devices = ActorSystem()
  try {
//    Log(EventLevel.Normal, "正在尝试杀灭重启ADB")
//    Runtime.getRuntime.exec("taskkill /F /IM adb.exe ")
//    Thread.sleep(300)
  } catch {
    case _ =>
  }
  private[Engine] val ADB = new AdbBackend(EngineSetting.ADBPath, false)
  Log(EventLevel.Normal, "ADB已激活，待命中")

  private[Data] val DeviceMap = new LinkedHashMap[String, ActorRef]


  /**
    * 连接一个Android真机
    *
    * @param AndroidDeviceID 安卓设备的设备ID
    * @return 如果成功连接，返回一个ADBDevice，否则返回空值
    */
  def NewAndroidDevFromADB(DevName: String): (String, Option[ActorRef]) = DevID.G(DevName) match {
    case None => Log(EventLevel.Warning, "【从ADB连接失败】设备名：" + DevName + " 未找到匹配的结果")
      ("", None)
    case s => NewAndroidDevFromADB(s.get._3, s.get._2, DevName)
  }

  def NewAndroidDevFromADB(AndroidDeviceID: String, DevType: String, DevName: String): (String, Option[ActorRef]) = {
    Log(EventLevel.Normal, "【尝试从ADB连接】设备名：" + DevName + "，ID：" + AndroidDeviceID + "，型号：" + DevType)
    try {
      ADB.waitForConnection(25000, AndroidDeviceID) match {
        case d: IChimpDevice => Log(EventLevel.Normal, "【ADB连接成功】设备ID为" + AndroidDeviceID)
          (DevName, Some(ActrSys_Devices.actorOf(Props(classOf[ADBDevice], "ADB-" + AndroidDeviceID, DevType: String, DevName, d))))
        case _ => Log(EventLevel.Warning, "【从ADB连接失败】设备名：" + DevName + "，ID：" + AndroidDeviceID)
          ("", None)
      }
    } catch {
      case e:com.android.ddmlib.ShellCommandUnresponsiveException =>  Log(EventLevel.Warning, "【从ADB连接失败】设备无响应：设备名：" + DevName + "，ID：" + AndroidDeviceID)
        ("ShellCommandUnresponsiveException", None)
      case e:Exception => Log(EventLevel.Warning, "【从ADB连接失败】设备名：" + DevName + "，ID：" + AndroidDeviceID)
        Log(EventLevel.Debug,e.toString)
        e.printStackTrace
        ("", None)
    }
  }


  def ADBReBind(DevName: String) = {
    synchronized {
      Log(EventLevel.Normal, "【尝试ADB重连】设备名：" + DevName)
      try {
        val ID = DevID.G(DevName).get
        ADB.waitForConnection(10240, ID._2) match {
          case d: IChimpDevice => Log(EventLevel.Normal, "【ADB连接成功】设备ID为" + ID)
          case _ => Log(EventLevel.Error, "【ADB重连失败】设备名：" + DevName + "，ID：" + ID)
            ("", None)
        }
      } catch {
        case _ => Log(EventLevel.Error, "【ADB重连失败】设备名：" + DevName)
          ("", None)
      }
    }
  }

  private def NewDev(DevName: String, DevActrRef: ActorRef) = {
    DeviceMap.put(DevName, DevActrRef)
    DevActrRef
  }


  def Log(Level: EventLevel, Context: String) = {
    Actors.Logger ! new EventLog(None, Level, Context)
  }
}
