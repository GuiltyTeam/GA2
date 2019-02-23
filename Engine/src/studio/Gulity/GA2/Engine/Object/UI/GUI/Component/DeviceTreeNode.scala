package studio.Gulity.GA2.Engine.Object.UI.GUI.Component

import akka.actor.ActorRef
import javax.swing.tree.DefaultMutableTreeNode
import studio.Gulity.GA2.Engine.Data.Devices

class DeviceTreeNode(val DevName: String) extends DefaultMutableTreeNode {
  var DevPnl: Option[GA2DevPnl] = None
  userObject=DevName

  def OnClick: Option[GA2DevPnl] = DevPnl match {
    case Some(pnl: GA2DevPnl) => Some(pnl)
    case None => Devices.NewAndroidDevFromADB(DevName)._2 match {
      case Some(d: ActorRef) => Some(new GA2DevPnl(d, DevDsplMode.Vertical))
      case None => None
    }
  }

}
