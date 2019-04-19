package cc.theurgist.motrack.ui.gui.controllers

import cc.theurgist.motrack.lib.dto.{Green, Red, ServerStatus, Yellow}
import javafx.scene.Parent
import javafx.scene.paint.Color
import scalafx.scene.control.{Label, Tooltip}
import scalafx.scene.shape.Circle
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class FxServerStatusLabel(
    val bulb: Circle,
    val infoLabel: Label
) {

  //val fxmlSSLabel: Parent = FXMLView(getClass.getResource("/jfx/controls/serverStatusLabel.fxml"), NoDependencyResolver)


  def updateServerStatus(ss: ServerStatus): Unit = {
    bulb.setFill(ss.health match {
      case Red    => new Color(1.0, 0.2, 0.2, 0.8)
      case Yellow => new Color(0.2, 1.0, 1.0, 0.3)
      case Green  => new Color(0.2, 1.0, 0.2, 0.2)
    })
    infoLabel.setText(s"Version: ${ss.version}")
    infoLabel.setTooltip(new Tooltip {
      text = s"Updated at ${ss.zonedTime}"
    }.delegate)
  }
}
