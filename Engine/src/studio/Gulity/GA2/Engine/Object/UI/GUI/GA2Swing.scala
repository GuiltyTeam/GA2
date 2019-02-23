package studio.Gulity.GA2.Engine.Object.UI.GUI

import java.awt.EventQueue

import javax.swing._
import net.miginfocom.swing.MigLayout
import java.awt.Window.Type
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.CardLayout
import java.awt.Dimension

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode
import java.awt.Font
import java.awt.event.{MouseAdapter, WindowEvent}

import akka.actor.ActorRef
import javax.swing.event.{TreeSelectionEvent, TreeSelectionListener}
import studio.Gulity.GA2.Engine.Data.Logger.EventLevel
import studio.Gulity.GA2.Engine.Data.{Devices, Logger}
import studio.Gulity.GA2.Engine.GA2System
import studio.Gulity.GA2.Engine.Object.UI.GUI.Component.{DevDsplMode, DevTree, DeviceTreeNode, GA2DevPnl}


/**
  * Created by Tamamo254 on 2019/01/20.
  */
object GA2Swing {
  /**
    * Launch the application.
    */
  def main(args: Array[String]): Unit = {
    EventQueue.invokeLater(new Runnable() {
      override def run(): Unit = {
        try {
          GA2System.Initialization
          val Frm_Main = new GA2Swing
          Logger.LogLevel = EventLevel.Debug
          Frm_Main.setVisible(true)


        } catch {
          case e: Exception =>
            e.printStackTrace()
        }
      }
    })
  }
}

class GA2Swing  extends JFrame {
  this.setType(Type.NORMAL)
  //        this.setUndecorated(true);
  this.setBounds(100, 100, 1600, 1040)
  this.setResizable(false)
  this.getContentPane.setLayout(new BorderLayout(0, 0))
  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)


  val Pnl_Body = new JPanel
  Pnl_Body.setBackground(Color.LIGHT_GRAY)
  this.getContentPane.add(Pnl_Body, BorderLayout.CENTER)
  Pnl_Body.setLayout(new CardLayout(0, 0))
  val splitPane = new JSplitPane
  splitPane.setContinuousLayout(true)
  splitPane.setOneTouchExpandable(true)
  splitPane.setEnabled(false)
  splitPane.setDividerSize(6)
  Pnl_Body.add(splitPane, "name_615256361998726")
  val tree = new DevTree {
    val RootNode = new DefaultMutableTreeNode("Devices")
    val AndroidNode = new DefaultMutableTreeNode("Android")
    AndroidNode.add(new DeviceTreeNode("Tester"))
    AndroidNode.add(new DeviceTreeNode("M3-0"))
    AndroidNode.add(new DeviceTreeNode("M3-1"))
    AndroidNode.add(new DeviceTreeNode("M3-2"))
    AndroidNode.add(new DeviceTreeNode("M3-3"))
    AndroidNode.add(new DeviceTreeNode("HWP9"))
    RootNode.add(AndroidNode)
    setModel(new DefaultTreeModel(RootNode))
    addTreeSelectionListener(new TreeSelectionListener() {
      override def valueChanged(e: TreeSelectionEvent): Unit = {
        getLastSelectedPathComponent match {
          case devnode: DeviceTreeNode => devnode.OnClick match {
            case Some(pnl: GA2DevPnl) =>Pnl_Devices.removeAll
              Pnl_Devices.add(pnl)
            case _ =>
          }
          case _ =>
        }
      }
    })
  }
  tree.setMinimumSize(new Dimension(128, 64))
  tree.setMaximumSize(new Dimension(128, 64))
  tree.setPreferredSize(new Dimension(128, 64))
  splitPane.setLeftComponent(tree)
  val Pnl_Devices = new JPanel
  splitPane.setRightComponent(Pnl_Devices)
  Pnl_Devices.setLayout(new CardLayout(0, 0))

//  Devices.NewAndroidDevFromADB("HWP9")._2 match {
//    case Some(d: ActorRef) => splitPane.setRightComponent(new GA2DevPnl(d, DevDsplMode.Vertical))
//    case _ =>
//  }

  def Reload(): Unit = {
  }
}
