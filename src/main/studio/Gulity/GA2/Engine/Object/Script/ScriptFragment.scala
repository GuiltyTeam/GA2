package studio.Gulity.GA2.Engine.Object.Script

import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel.EventLevel
import studio.Gulity.GA2.Engine.Object.Dev.Key
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib, Recognizable}

/**
  * Created by Tamamo254 on 2019/1/14.
  */
private[GA2] abstract class ScriptFragment(val ParentScript: Script) extends Controllable with Recognizable {
 def Log(Level: EventLevel, Context: String):Unit = ParentScript.Log(Level, "[" + this.getClass.getName.substring(14) + "]" + Context)

 def Log(Context: String):Unit  = ParentScript.Log(EventLevel.Normal, Context)

 def Tap(X: Int, Y: Int) = ParentScript.Tap(X, Y)

 def Double(X: Int, Y: Int) = ParentScript.Double(X, Y)

 def Drag(X: Int, Y: Int, NewX: Int, NewY: Int) = ParentScript.Drag(X, Y, NewX, NewY)

 def Drag(Points: Array[Array[Int]]) = ParentScript.Drag(Points)

 def Press(Key: Key) = ParentScript.Press(Key)

 def Sleep = ParentScript.Sleep

 def Wake = ParentScript.Wake

 def Wait(ms: Int): Boolean = ParentScript.Wait(ms)

 def ScrShot = ParentScript.ScrShot

 def Get_Color(X: Int, Y: Int): Color = ParentScript.Get_Color(X, Y)

 def Check_Color(X: Int, Y: Int, Clr: Color) = ParentScript.Check_Color(X, Y, Clr)

 def Check_Color(X: Int, Y: Int, Clr: Color, Tolerance: Int) = ParentScript.Check_Color(X, Y, Clr, Tolerance)

 def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int, DML: DotMatrixLib) = ParentScript.Get_Text(X, Y, OtrX, OtrY, DML)

 def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix) = ParentScript.Check_DotMatrix(X, Y, OtrX, OtrY, DM)

 def Enter_Text(Text: String) = ParentScript.Enter_Text(Text)
}
