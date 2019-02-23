package studio.Gulity.GA2.Engine

import com.sun.jna.Native

import studio.Gulity.GA2.Engine.Data.{Actors, Devices}

object GA2System {
  val SysPath=System.getProperty("user.dir")

  def Initialization(): Unit = {
//    JNADLLLoader("Win64")
    Actors
    Devices
  }

  def JNADLLLoader(OS:String)=OS match {
    case "Win32"=>
      System.load(SysPath+"/lib/Tess4J/win32-x86/libtesseract3051.dll")
    case "Win64"=>
      System.load(SysPath+"/lib/Tess4J/win32-x86-64/libtesseract3051.dll")
  }

  def Shutdown(): Unit = {
    Actors.ActrSys.terminate
    Devices.ActrSys_Devices.terminate
    Devices.ADB.shutdown
  }
}
