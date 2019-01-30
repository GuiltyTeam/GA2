package studio.Gulity.GA2.Engine.Object.Dev.Bind.Mobile.ADB

import java.awt.{Graphics2D, RenderingHints}
import java.awt.image.BufferedImage

import com.android.chimpchat.core.{IChimpDevice, TouchPressType}
import studio.Gulity.GA2.Engine.Data.Devices
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.Device
import studio.Gulity.GA2.Engine.Object.Dev.Bind.Mobile.AndroidDevice
import studio.Gulity.GA2.Engine.Object.Dev.{DeviceType, Key}
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib}
import studio.Gulity.GA2.Engine.Object.UI.GUI.Component.GA2DevPnl

/**
  * Created by Tamamo254 on 2017/7/21.
  */
private[Engine] class ADBDevice(override val DevID: String, override val DevModelType: String, override val DevName: String, private var ICDev: IChimpDevice) extends AndroidDevice(DevID, DevModelType, DevName) {
  var ADBBind: IChimpDevice = ICDev
  DevBind = ADBBind
  Wait(100)
  override val AndroidDevID = DevID


  override def ScrShot {
    Log(EventLevel.Debug, "重新截屏")
    Scr = ADBBind.takeSnapshot.getBufferedImage
    Log(EventLevel.Debug, "截屏成功")
    //    var MixScr = Scr.
    if (GUIPnl.isDefined) {
      GUIPnl.get.UpdateScr(Scr)
      //      {
      //        G2D= Scr.createGraphics().setRenderingHint(
      //          RenderingHints.KEY_INTERPOLATION,
      //          RenderingHints.VALUE_INTERPOLATION_BILINEAR)
      //        G2D.drawImage()
      //      })
      Log(EventLevel.Debug, "转发给GUI")
    }
  }

  override def Get_Color(X: Int, Y: Int): Color = {
    val Clr = new Color(Scr.getRGB(X, Y))
    Log(EventLevel.Debug, "取色[" + X + "，" + Y + "]=" + Clr)
    Clr
  }

  override def Tap(X: Int, Y: Int) = {
    Log(EventLevel.Debug, "点击[" + X + "，" + Y + "]")
    ADBBind.touch(X, Y, TouchPressType.DOWN_AND_UP)
  }

  override def Double(X: Int, Y: Int) {}

  override def Drag(X: Int, Y: Int, NewX: Int, NewY: Int): Unit = {
    ADBBind.drag(X, Y, NewX, NewY, 3, 1024)
  }

  override def Drag(Points: Array[Array[Int]]) {}

  override def Press(Key: Key): Unit = {
    if (Key.DevType == DeviceType.Android) ADBBind.press(Key.Value.toString, TouchPressType.DOWN_AND_UP)
  }

  override def Sleep(): Unit = {
    Devices.ADBReBind(DevName)
  }

  override def Wake: Unit = ADBBind.wake()

  override def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int, DML: DotMatrixLib): Unit = ???

  override def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix): Unit = ???

  override def Reset: Unit = {
    ADBBind.shell("killall com.android.commands.monkey")
    DevBind = Devices.ADBReBind(DevName)
  }

  override def Enter_Text(Text: String): Unit = ADBBind.`type`(Text)

  override def DevShell(Cmd: String) = ADBBind.shell(Cmd)

  override def DisCnt: Unit = DevShell("killall com.android.commands.monkey")

}
