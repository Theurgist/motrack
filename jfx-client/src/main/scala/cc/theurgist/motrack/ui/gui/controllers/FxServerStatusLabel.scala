package cc.theurgist.motrack.ui.gui.controllers

import cc.theurgist.motrack.lib.dto.{Green, Red, ServerStatus, Yellow}
import com.typesafe.scalalogging.StrictLogging
import javafx.scene.paint.Color
import scalafx.scene.control.{Label, Tooltip}
import scalafx.scene.input.MouseEvent
import scalafx.scene.shape.Circle
import scalafxml.core.macros.sfxml

import scala.util.Random

@sfxml
class FxServerStatusLabel(
    val bulb: Circle,
    val infoLabel: Label
) extends StrictLogging {
  logger.info("awakens: FxServerStatusLabel")

//  def load(): Parent = {
//    val loader = new FXMLLoader(getClass.getResource("/jfx/controls/serverStatusLabel.fxml"), NoDependencyResolver)
//    loader.setRoot(this)
//    loader.setController(this)
//
//    try {
//      loader.load()
//    } catch {
//      case e: IOException => logger.error(s"Can't load: $e")
//    }
//    val fxmlSSLabel: Parent =
//      FXMLView(getClass.getResource("/jfx/controls/serverStatusLabel.fxml"), NoDependencyResolver)
//    fxmlSSLabel
//  }

  def bulbClicked(e: MouseEvent): Unit = {
    bulb.setFill(new Color(Random.nextDouble(), Random.nextDouble(), Random.nextDouble(), 1.0))
  }

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
