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


}
