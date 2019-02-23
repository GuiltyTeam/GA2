package studio.Gulity.GA2.Engine.Object.Dev.Bind.Mobile

import studio.Gulity.GA2.Engine.Object.Dev.{Device, DeviceType}


/**
  * Created by Tamamo254 on 2017/7/22.
  */
private[GA2] abstract class AndroidDevice(override val DevID: String, override val DevModelType: String, override val DevName: String) extends Device(DevID, DevModelType, DevName) {
  override val DevType = DeviceType.Android
  private[Engine] val AndroidDevID: String
}
