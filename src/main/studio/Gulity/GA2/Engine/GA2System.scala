package studio.Gulity.GA2.Engine

import studio.Gulity.GA2.Engine.Data.{Actors, Devices}

object GA2System {
  def Initialization(): Unit = {
    Actors
    Devices
  }

  def Shutdown(): Unit = {
    Actors.ActrSys.terminate
    Devices.ActrSys_Devices.terminate
    Devices.ADB.shutdown
  }
}
