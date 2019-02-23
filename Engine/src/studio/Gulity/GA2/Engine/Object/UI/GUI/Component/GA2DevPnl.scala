package studio.Gulity.GA2.Engine.Object.UI.GUI.Component

import net.miginfocom.swing.MigLayout
import javax.swing._
import java.awt._
import java.awt.event._
import java.awt.image.BufferedImage

import akka.actor.ActorRef
import studio.Gulity.GA2.Engine.Object.UI.GUI.Component.DevDsplMode.DevDsplMode
import studio.Gulity.GA2.Engine.Object.Dev.Device

object DevDsplMode extends Enumeration {
  type DevDsplMode = Value
  val Horizontal, Vertical = Value
}

class GA2DevPnl(val DevActorRef: ActorRef, DevMode: DevDsplMode) extends JPanel {

  var DevObj: Option[Device] = None

  def this(DevActorRef: ActorRef) {
    this(DevActorRef, DevDsplMode.Vertical)
  }

  def Ping = DevActorRef ! this

  def Pong(Dev: Device): Unit = {
    DevObj = Some(Dev)
  }

  this.setLayout(new MigLayout("", "[560px][grow]", "[980][grow]"))

  val Pnl_Screen = new ScreenPnl(this)
  this.add(Pnl_Screen, "cell 0 0,alignx center,aligny center")


  val TxtAra_Console = new JTextArea
  TxtAra_Console.setEditable(false)
//  this.add(new JScrollPane(TxtAra_Console), "flowy,cell 1 0 1 2,grow")
  this.add(TxtAra_Console, "flowy,cell 1 0 1 2,grow")

  val Separator = new JSeparator
  this.add(Separator, "cell 1 0")

  val Pnl_Console_Input = new JPanel
  this.add(Pnl_Console_Input, "cell 1 0,growx")
  Pnl_Console_Input.setLayout(new BorderLayout(0, 0))

  val Btn_Console_Input = new JButton("\u786E\u8BA4[Enter]") {
    addActionListener((e: ActionEvent) => {
      ConsoleInput(TxtFld_Console_Input.getText)
    })
  }
  Pnl_Console_Input.add(Btn_Console_Input, BorderLayout.EAST)

  val TxtFld_Console_Input = new JTextField
  TxtFld_Console_Input.setColumns(10)
  TxtFld_Console_Input.addKeyListener(new KeyListener() {
    override def keyPressed(e: KeyEvent) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        ConsoleInput(TxtFld_Console_Input.getText)
      }
    }

    override def keyTyped(e: KeyEvent): Unit = {}

    override def keyReleased(e: KeyEvent): Unit = {}
  })

  Pnl_Console_Input.add(TxtFld_Console_Input, BorderLayout.CENTER)

  val Pnl_Ctrls = new JPanel
  this.add(Pnl_Ctrls, "cell 0 1,growx")

  Ping

  def UpdateScr(Scr: BufferedImage) = {
    println("刷新屏幕！")
    Pnl_Screen.Dspl(Scr)
  }

  def UpdateConsole(Log: String) = {
    TxtAra_Console.append("\n" + Log)
  }

  def ConsoleInput(Cmd: String): Unit = {
    UpdateConsole("\n" + "[" + DevObj.get.DevID + "]" + "->" + Cmd)
    DevActorRef ! Cmd
    TxtFld_Console_Input.setText("")
  }


}