package studio.Gulity.GA2.Engine.Object.Recognition

import java.awt.image.BufferedImage

import net.sourceforge.tess4j.{ITesseract, Tesseract}
import studio.Gulity.GA2.Engine.Cfg.EngineSetting

private[Engine] trait Recognizable {



  def ScrShot

  var IT: ITesseract = new Tesseract()

  def Get_Color(X: Int, Y: Int): Color

  def Check_Color(X: Int, Y: Int, Clr: Color, Tolerance: Int = EngineSetting.DfltClrTolerance): Boolean

  def Get_Text(X: Int, Y: Int, OtrX: Int, OtrY: Int): String

  def Check_DotMatrix(X: Int, Y: Int, OtrX: Int, OtrY: Int, DM: DotMatrix)

  def Enter_Text(Text: String)
}
