package studio.Gulity.GA2.Engine.Object.Recognition

import java.awt.image.BufferedImage

final class DotMatrix(Dots: Array[Array[Boolean]]) {
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

  def CheckDotMatrix(Image: BufferedImage,Color:Color): Unit ={

  }

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

}