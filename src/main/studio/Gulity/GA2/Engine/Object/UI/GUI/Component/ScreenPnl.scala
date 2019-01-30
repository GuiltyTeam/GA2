package studio.Gulity.GA2.Engine.Object.UI.GUI.Component

import java.awt.event.{MouseEvent, MouseListener}
import java.awt.{CardLayout, Color, Dimension, Graphics}
import java.awt.image.BufferedImage

import javax.swing.{JLabel, JPanel}

class ScreenPnl(Parent: GA2DevPnl) extends JPanel {

  var Scr: BufferedImage = new BufferedImage(1, 1, 1)

  setLayout(new CardLayout)
  setBackground(Color.WHITE)
  setMaximumSize(new Dimension(540, 960))
  setSize(new Dimension(540, 960))
  setMinimumSize(new Dimension(540, 960))
  val Lbl_Screen = new JLabel("Loading...")

  def Dspl(ScrSt: BufferedImage) = {
    Scr = ScrSt
    Lbl_Screen.setText("")
    repaint()
  }

  override def paintComponent(G: Graphics): Unit = {
    G.drawImage(Scr, 0, 0, 540, 960, null)
  }

  addMouseListener(new MouseListener {
    override def mouseClicked(e: MouseEvent): Unit = {

      if (e.getButton == MouseEvent.BUTTON1) {
        Tap(e.getX.toShort,e.getY.toShort)
      }else if (e.getButton == MouseEvent.BUTTON3) {
        PrintInfo(e.getX.toShort,e.getY.toShort)
      }
    }

    override def mousePressed(e: MouseEvent): Unit = {}

    override def mouseReleased(e: MouseEvent): Unit = {}

    override def mouseEntered(e: MouseEvent): Unit = {}

    override def mouseExited(e: MouseEvent): Unit = {}
  })

  def PrintInfo(X: Short, Y: Short): Unit = {
    val x = 2 * X - 1
    val y = 2 * Y - 1
    Parent.ConsoleInput("当前坐标：" + x + "," + y)
    Parent.DevActorRef ! "GetClr_ScptMkr:" + x + "," + y
  }

  def Tap(X: Short, Y: Short): Unit = {
    val x = 2 * X - 1
    val y = 2 * Y - 1
    Parent.DevActorRef ! "Tap_ScptMkr:" + x + "," + y
  }
}
