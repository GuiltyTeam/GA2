package studio.Gulity.GA2.Engine.Object.Script

import studio.Gulity.GA2.Engine.Object.Dev.Key
import studio.Gulity.GA2.Engine.Object.Recognition.{Color, DotMatrix, DotMatrixLib}

private[Engine] trait Controllable {

  def Tap(X: Int, Y: Int)

  def Double(X: Int, Y: Int)

  def Drag(X: Int, Y: Int, NewX: Int, NewY: Int)

  def Drag(Points: Array[Array[Int]])

  def Press(Key: Key)

  def Sleep

  def Wake

  def Wait(ms: Int): Boolean

  def ScrShot

  def Get_Color(X: Int, Y: Int): Color

  def Check_Color(X: Int, Y: Int, Clr: Color):Boolean

  def Check_Color(X: Int, Y: Int, Clr: Color, Tolerance: Int):Boolean

  def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int, DML: DotMatrixLib)

  def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix)

  def Enter_Text(Text: String)
}
