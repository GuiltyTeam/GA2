package studio.Gulity.GA2.Engine.Object.Script

import studio.Gulity.GA2.Engine.Enum.AndroidPhysicalButton
import studio.Gulity.GA2.Engine.Object.Dev.Bind.Mobile.AndroidDevice

/**
  * Created by Tamamo254 on 2017/7/18.
  */
abstract class ModelTypeHelper(override val Dev: AndroidDevice) extends Script(Dev) {

  def AllClear()

  def QuickStart(AppLctn: Int)

}
