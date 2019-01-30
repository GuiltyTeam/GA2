package studio.Gulity.GA2.Engine.Object.Dev

import studio.Gulity.GA2.Engine.Object.Dev.DeviceType.DeviceType

/**
  * Created by Tamamo254 on 2019/1/13.
  */
final class Key(val DeviceType: DeviceType, val KeyName: String, val KeyValue: String) {
  def DevType = DeviceType

  def Name = KeyName

  def Value = KeyValue
}
