package studio.Gulity.GA2.中文包

import java.io.IOException

import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.{Device, Key}
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib}
import studio.Gulity.GA2.Engine.Object.Script.Script
import studio.Gulity.GA2.中文包.事件等级.事件等级

abstract class 脚本(val 设备: Device) extends Script(设备) {

  Log(EventLevel.Normal, "脚本激活：" + this.getClass.getName)

  def 设备名 = Dev.DevName


  def Receive(Cmd: String): Boolean = 控制台指令(Cmd)

  def 控制台指令(指令文本: String):Boolean

  def 日志(等级: 事件等级, 内容: String) = Dev.Log(EventLevel.apply(等级.id), "[" + this.getClass.getName.substring(14) + "]" + 内容)

  def 日志(内容: String) = Dev.Log(EventLevel.Normal, 内容)

  def 点击(X: Int, Y: Int) = Dev.Tap(X, Y)

  def 双击(X: Int, Y: Int) = Dev.Double(X, Y)

  def 拖拽(X: Int, Y: Int, NewX: Int, NewY: Int) = Dev.Drag(X, Y, NewX, NewY)

  def 拖拽(Points: Array[Array[Int]]) = Dev.Drag(Points)

  def 按键(键位: Key) = Dev.Press(键位)

  def 休眠 = Dev.Sleep

  def 唤醒 = Dev.Wake

  def 等待(延时毫秒数: Int): Boolean = Dev.Wait(延时毫秒数)

  def 截屏 = Dev.ScrShot

  def 取色(X: Int, Y: Int): Color = Dev.Get_Color(X, Y)

  def 验色(X: Int, Y: Int, Clr: Color) = Dev.Check_Color(X, Y, Clr)

  def 验色(X: Int, Y: Int, Clr: Color, Tolerance: Int) = Dev.Check_Color(X, Y, Clr, Tolerance)

  def 识别文字(X: Int, Y: Int, OtrX: Int, OtrY: Int) = Dev.Get_Text(X, Y, OtrX, OtrY)

  def 识别点阵(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix) = Dev.Check_DotMatrix(X, Y, OtrX, OtrY, DM)

  def 键入文字(文本: String) = Dev.Enter_Text(文本)


}
