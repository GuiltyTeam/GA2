package studio.Gulity.GA2.Engine.Object.Recognition

import java.awt.image.BufferedImage

import scala.util.control.Breaks._

class DotMatrix(Dots: Array[Array[Boolean]]) {
  def this(X: Short, Y: Short) {
    this(Array.ofDim[Boolean](X, Y))
  }

  def this(DotMatrixString: String) {
    this(DotMatrixString.split("\n")(0).length.toShort, DotMatrixString.split("\n").length.toShort)
    val SplitedStr = DotMatrixString.split("\n")
    for (i <- Dots.indices) {
      for (j <- Dots(i).indices) {
        Dots(i)(j) = SplitedStr(i).charAt(j) == "█"
      }
    }
  }

  //  def this(Img: BufferedImage, ColorFilter: Color) {
  //    this(DotMatrixString.split("\n")(0).length.toShort, DotMatrixString.split("\n").length.toShort)
  //    val SplitedStr = DotMatrixString.split("\n")
  //    for (i <- Dots.indices) {
  //      for (j <- Dots(i).indices) {
  //        Dots(i)(j) = SplitedStr(i).charAt(j) == "█"
  //      }
  //    }
  //  }


  override def toString: String = {
    val SB = new StringBuilder()
    for (i <- Dots.indices) {
      for (j <- Dots(i).indices) {
        SB.append(if (Dots(i)(j)) "█" else "　")
      }
      SB.append("\n")
    }
    SB.toString
  }

  def Trim: DotMatrix = {
    for (i <- 0 to 4) {
      breakable {

      }
    }
    this
  }

  def TrimSelf: DotMatrix = {
    this
  }

  private def EmptyCln(Nbr: Short): Boolean = {
    var Rtn = true
    breakable {
      Dots.foreach(
        Cln => if (Cln(Nbr)) {
          Rtn = false
          break
        })
    }
    Rtn
  }

  private def EmptyRow(Nbr: Short): Boolean = {
    var Rtn = true
    breakable {
      Dots(Nbr).foreach(
        Dot => if (Dot) {
          Rtn = false
          break
        })
    }
    Rtn
  }

  def Width = Dots(0).length

  def Height = Dots.length
}