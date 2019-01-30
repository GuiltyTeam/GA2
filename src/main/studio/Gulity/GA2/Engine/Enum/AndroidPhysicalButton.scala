package studio.Gulity.GA2.Engine.Enum

import studio.Gulity.GA2.Engine.Object.Dev.{DeviceType, Key}

object AndroidPhysicalButton {
  val Home = new Key(DeviceType.Android, "Home", "KEYCODE_HOME")
  val Search = new Key(DeviceType.Android, "Search", "KEYCODE_SEARCH")
  val Menu = new Key(DeviceType.Android, "Menu", "KEYCODE_MENU")
  val Back = new Key(DeviceType.Android, "Back", "KEYCODE_BACK")
  val DPad_UP = new Key(DeviceType.Android, "DPad_UP", "DPAD_UP")
  val DPad_DOWN = new Key(DeviceType.Android, "DPad_DOWN", "DPAD_DOWN")
  val DPad_LEFT = new Key(DeviceType.Android, "DPad_LEFT", "DPAD_LEFT")
  val DPad_RIGHT = new Key(DeviceType.Android, "DPad_RIGHT", "DPAD_RIGHT")
  val DPad_CENTER = new Key(DeviceType.Android, "DPad_CENTER", "DPAD_CENTER")
  val Enter = new Key(DeviceType.Android, "Enter", "enter")
}
