package studio.Gulity.GA2.Engine.Object.Script

import java.io.IOException

import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.{Device, Key}
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib, Recognizable}

/**
  * Created by Tamamo254 on 2017/7/18.
  */
abstract class Script(val Dev: Device) extends Controllable with Recognizable {


  Log(EventLevel.Normal, "脚本激活：" + this.getClass.getName)

  def DevName = Dev.DevName

  private[Engine] def receive(Cmd: String) = {
    Log(EventLevel.Debug, "收到脚本命令：" + Cmd)
    try {
      if (!Receive(Cmd)) Log(EventLevel.Warning, "【Script错误】未定义的错误指令：" + Cmd)
    } catch {
      case e: java.net.SocketException => Log(EventLevel.Error, "ADB传输错误！正在重连")
        Dev.Reset
        Receive(Cmd)
      case e: IOException => Log(EventLevel.Error, "ADB传输错误！请断开手机重连")
      case _ =>
    }

  }

  def Receive(Cmd: String): Boolean

  protected def SplitCmd(Cmd: String): Array[String] = Array(Cmd.split(':')(0)) ++ Cmd.split(':')(1).split(",")

  def Log(Level: EventLevel, Context: String) = Dev.Log(Level, Context)

  def Log(Context: String) = Dev.Log(EventLevel.Normal, Context)

  def Tap(X: Int, Y: Int) = Dev.Tap(X, Y)

  def Double(X: Int, Y: Int) = Dev.Double(X, Y)

  def Drag(X: Int, Y: Int, NewX: Int, NewY: Int) = Dev.Drag(X, Y, NewX, NewY)

  def Drag(Points: Array[Array[Int]]) = Dev.Drag(Points)

  def Press(Key: Key) = Dev.Press(Key)

  def Sleep = Dev.Sleep

  def Wake = Dev.Wake

  def Wait(ms: Int): Boolean = Dev.Wait(ms)

  def ScrShot = Dev.ScrShot

  def Get_Color(X: Int, Y: Int): Color = Dev.Get_Color(X, Y)

  def Check_Color(X: Int, Y: Int, Clr: Color) = Dev.Check_Color(X, Y, Clr)

  def Check_Color(X: Int, Y: Int, Clr: Color, Tolerance: Int) = Dev.Check_Color(X, Y, Clr, Tolerance)

  def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int) = Dev.Get_Text(X, Y, OtrX, OtrY)

  def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix) = Dev.Check_DotMatrix(X, Y, OtrX, OtrY, DM)

  def Enter_Text(Text: String) = Dev.Enter_Text(Text)
}
