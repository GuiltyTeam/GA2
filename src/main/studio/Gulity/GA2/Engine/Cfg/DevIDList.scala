package studio.Gulity.GA2.Engine.Cfg

import scala.collection.mutable

/**
  * Created by Tamamo254 on 2017/7/21.
  */
abstract class DevIDList {
  private val Map = new mutable.HashMap[String, (String, String, String)]

  def Dev(DevName: String, DevType: String, DevModel: String, DevID: String) = Map.put(DevName, (DevType, DevModel, DevID))

  def G(DevName: String) = Map.get(DevName)


}
