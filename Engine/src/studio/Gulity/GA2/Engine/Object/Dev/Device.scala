package studio.Gulity.GA2.Engine.Object.Dev

import java.awt.Graphics2D
import java.awt.image.BufferedImage

import akka.actor.Actor
import akka.japi.Option.Some
import studio.Gulity.GA2.Engine.Cfg.EngineSetting
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Data.{Actors, EventLog, Logger}
import studio.Gulity.GA2.Engine.Object.Dev.DeviceType.DeviceType
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib, Recognizable}
import studio.Gulity.GA2.Engine.Object.Script.{Controllable, ModelTypeHelper, Script}
import studio.Gulity.GA2.Engine.Object.UI.GUI.Component.GA2DevPnl

/**
  * Created by Tamamo254 on 2017/7/17.
  */
private[GA2] abstract class Device(val DevID: String, val DevModelType: String, val DevName: String, var GUIPnl: Option[GA2DevPnl]) extends Actor with Controllable with Recognizable {
  private[Dev] val DevType: DeviceType
  private[Dev] var DevBind: Any = 0
  private[Dev] var ScptMkr: Boolean = false
  private[Dev] var Scr: BufferedImage = new BufferedImage(1, 1, 1)
  private[Dev] var G2D: Graphics2D = Scr.createGraphics()
  private[Dev] var RunningScpt: Option[studio.Gulity.GA2.Engine.Object.Script.Script] = None
  private[Dev] val ModelTypeHelperScript: ModelTypeHelper = TryRunModelTypeHelperScript

  def this(DevID: String, DevModelType: String, DevName: String) {
    this(DevID, DevModelType, DevName, None)
  }


  def receive = {
    case cls: Class[Script] if RunningScpt.isEmpty => RunNewScript(cls)

    case "AllClear" => ModelTypeHelperScript.AllClear
    case "AClr" => ModelTypeHelperScript.AllClear
    case "ScptMkr" => ScptMkr = true
      Logger.LogLevel = EventLevel.Debug
      Log(EventLevel.Normal, "脚本写作模式已激活")

    case str: String if str.matches("^Start:[1-4]{1}$") => ModelTypeHelperScript.QuickStart(str.substring(6).toShort)
    case str: String if RunningScpt.isDefined && str.matches("^Scpt.[-_a-zA-Z0-9:]+$") => RunningScpt.get.receive(str.substring(6))
    case str: String if RunningScpt.isEmpty && str.matches("^Scpt.[-_a-zA-Z0-9:]+$") => Log(EventLevel.Warning, "【Device：Actor错误】当前没有已经激活的脚本：" + str)
    case str: String if RunningScpt.isEmpty && str.matches("^Run:[-_a-zA-Z0-9:]+$") =>
      Log(EventLevel.Normal, "打开脚本" + "Scripts." + str.substring(4) + "." + DevModelType + ".Index")
      RunNewScriptFromClassName(str.substring(4))


    case str: String if str.matches("^Tap_ScptMkr:[0-9]+,[0-9]+$") =>
      val Cmd = SplitCmd(str)
      Tap_ScptMkr(Cmd(1).toShort, Cmd(2).toShort)
    case "ScrShot" => ScrShot
    case "SS" => ScrShot
    case str: String if str.matches("^GetClr_ScptMkr:[0-9]+,[0-9]+$") =>
      val Cmd = SplitCmd(str)
      Get_Color_ScptMkr(Cmd(1).toShort, Cmd(2).toShort)
    case str: String if str.matches("^Drag_ScptMkr:[0-9]+,[0-9]+,[0-9]+,[0-9]+$") =>
      val Cmd = SplitCmd(str)
      Drag(Cmd(1).toShort, Cmd(2).toShort, Cmd(3).toShort, Cmd(4).toShort)


    case "Reset" => Reset
    case "DisCnt" => DisCnt

    case str: String if str.isEmpty || str == "Scpt." => Log(EventLevel.Warning, "【Device：Actor错误】空指令")
    case str: String if RunningScpt.isDefined => RunningScpt.get.receive(str)
    case guipnl: GA2DevPnl =>
      Log(EventLevel.Normal, "有GUI界面试图连接")
      this.GUIPnl = Some(guipnl)
      guipnl.Pong(this)
      Wait(1000)
      ScrShot
    case _ => Log(EventLevel.Warning, "【Device：Actor错误】未定义的错误对象传入")
  }

  protected def SplitCmd(Cmd: String): Array[String] = Array(Cmd.split(':')(0)) ++ Cmd.split(':')(1).split(",")

  protected def TryRunModelTypeHelperScript: ModelTypeHelper = Class.forName("studio.Gulity.GA2.Scripts.ModelType." + DevModelType + ".Helper") match {
    case cls: Class[ModelTypeHelper] => cls.getConstructors.array(0).newInstance(this).asInstanceOf[ModelTypeHelper]
    case _ => Log(EventLevel.Error, "未定义该型号设备的默认辅助脚本！请定义在：studio.Gulity.GA2.Scripts.ModelType." + DevModelType + ".Helper")
      throw new ClassNotFoundException
  }

  protected def RunNewScript(Cls: Class[Script]): Script = {
    RunningScpt = Some(Cls.getConstructors.array(0).newInstance(this).asInstanceOf[Script])
    RunningScpt.get
  }

  protected def RunNewScriptFromClassName(ClsName: String): Script = Class.forName("studio.Gulity.GA2.Scripts." + ClsName + "." + DevModelType + ".Index") match {
    case cls: Class[Script] => RunNewScript(cls)
    case _ => Log(EventLevel.Error, "未定义该系列辅助脚本！请定义在：studio.Gulity.GA2.Scripts." + ClsName + "." + DevModelType)
      throw new ClassNotFoundException
  }

  def ID: String = DevID

  def Type = DevType

  def Log(Level: EventLevel, Context: String) = {
    Actors.Logger ! new EventLog(Some(this), Level, Context)
  }

  def Tap(X: Int, Y: Int)

  def Tap_ScptMkr(X: Int, Y: Int) = {
    Tap(X, Y)
    Log(EventLevel.Debug, "点击该点的语句为：Tap(" + X + "," + Y + ")")
  }

  def Double(X: Int, Y: Int)

  def Drag(X: Int, Y: Int, NewX: Int, NewY: Int)

  def Drag(Points: Array[Array[Int]])

  def Press(Key: Key)

  def Sleep

  def Wake

  def Wait(ms: Int): Boolean = {
    Log(EventLevel.Debug, "等待[" + ms + "ms]")
    try {
      Thread.sleep(ms)
      true
    } catch {
      case t: Throwable =>
        print(t.getMessage)
        false
    }
  }

  def ScrShot

  def Get_Color(X: Int, Y: Int): Color

  def Get_Color_ScptMkr(X: Int, Y: Int): Unit = Log(EventLevel.Debug, "检查该点颜色的语句为：Check_Color(" + X + ", " + Y + ", new Color(\"" + Get_Color(X, Y).HexValue + "\"))")

  def Check_Color(X: Int, Y: Int, Clr: Color, Tolerance: Int = EngineSetting.DfltClrTolerance): Boolean = {
    val Bool: Boolean = Get_Color(X, Y).Distance(Clr) <= Tolerance
    Log(EventLevel.Debug, "验色[" + X + "，" + Y + "，" + Clr + "] = " + Bool)
    Bool
  }

  def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int)={
    IT.setDatapath("./res/")
    ScrShot
    println(Scr.getSubimage(X, Y, OtrX - X, OtrY - Y).getColorModel)
    IT.doOCR(Scr.getSubimage(X, Y, OtrX - X, OtrY - Y))
  }

  def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix)

  def Enter_Text(Text: String)


  def Reset

  def DisCnt

  def Shell(Cmd: String)
}

object DeviceType extends Enumeration {
  type DeviceType = Value
  val None = Value("None")
  val PC = Value("PC")
  val Android = Value("Android")
}