package studio.Gulity.GA2.中文包

import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.Value

object 事件等级 extends Enumeration {
  type 事件等级 = Value
  val 调试 = Value(0)
  val 通知 = Value(1)
  val 警告 = Value(2)
  val 错误 = Value(3)
  val 崩溃 = Value(4)
}
