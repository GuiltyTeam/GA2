package studio.Gulity.GA2.Engine.Object.Recognition

/**
  * Created by Tamamo254 on 2017/7/21.
  */
final class Color(val RGB: Int) {

  def this(R: Short, G: Short, B: Short) {
    this(((R & 0xFF) << 16) | ((G & 0xFF) << 8) | ((B & 0xFF) << 0))
  }

  def this(R: Int, G: Int, B: Int) {
    this(((R & 0xFF) << 16) | ((G & 0xFF) << 8) | ((B & 0xFF) << 0))
  }


  def this(HexStr: String) {
    this(Integer.decode(HexStr).intValue())
  }

  override def toString: String = HexValue

  def R: Int = (RGB >> 16) & 0xFF

  def G: Int = (RGB >> 8) & 0xFF

  def B: Int = (RGB >> 0) & 0xFF

  def HexValue: String = {
    val Hex = new StringBuilder("#")
    if (R >= 16) {
      Hex.append(Integer.toHexString(R))
    } else {
      Hex.append("0").append(Integer.toHexString(R))
    }
    if (G >= 16) {
      Hex.append(Integer.toHexString(G))
    } else {
      Hex.append("0").append(Integer.toHexString(G))
    }
    if (B >= 16) {
      Hex.append(Integer.toHexString(B))
    } else {
      Hex.append("0").append(Integer.toHexString(B))
    }
    Hex.toString
  }

  def Distance(Clr: Color): Int = Math.abs(R - Clr.R) + Math.abs(G - Clr.G) + Math.abs(B - Clr.B)
}
