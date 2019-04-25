package cc.theurgist.motrack.ui.gui.controllers

import cc.theurgist.motrack.lib.dto.{Green, Red, ServerStatus, Yellow}
import com.typesafe.scalalogging.StrictLogging
import javafx.scene.paint.Color
import scalafx.scene.AccessibleRole.TextArea
import scalafx.scene.Scene
import scalafx.scene.control.{ContextMenu, Label, Tab, TabPane, TextArea, Tooltip}
import scalafx.scene.input.{MouseButton, MouseEvent}
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.shape.Circle
import scalafx.scene.text.Font
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

import scala.util.Random

trait ServerStatusLabelController {
  def bulbClicked(e: MouseEvent): Unit
  def updateServerStatus(ss: ServerStatus): Unit
  def updateErrorLabel(errorMsg: String): Unit
}

@sfxml
class FxServerStatusLabelController(
    val bulb: Circle,
    val infoLabel: Label,
    val infoTooltip: Tooltip,
    val errorLabel: Label,
    val errorTooltip: Tooltip,
) extends HBox with ServerStatusLabelController with StrictLogging {
  logger.trace("awakens: FxServerStatusLabel")

  updateServerStatus(ServerStatus(Yellow, "Connecting..."))
  updateErrorLabel("")

  def bulbClicked(e: MouseEvent): Unit =
    bulb.setFill(new Color(Random.nextDouble(), Random.nextDouble(), Random.nextDouble(), 1.0))

  def errorLabelClicked(e: MouseEvent): Unit = {
    if (e.button == MouseButton.Primary && e.getClickCount == 2)
      updateErrorLabel("")
    else if (e.button == MouseButton.Secondary) {

      val xPos = e.screenX
      val yPos = e.screenY - 350

      val stage = new Stage() {
        x = xPos
        y = yPos
        scene = new Scene(500, 300) {
          root = new BorderPane() {
            center = new TextArea() {
              text = errorLabel.text.get()
                wrapText = true
                font = new Font(16)
            }
          }
        }
        title = "Last error"
      }
      stage.show()
    }
  }

  def updateServerStatus(ss: ServerStatus): Unit = {
    bulb.setFill(ss.health match {
      case Red    => new Color(1.0, 0.2, 0.2, 0.8)
      case Yellow => new Color(0.2, 1.0, 1.0, 0.9)
      case Green  => new Color(0.2, 1.0, 0.2, 0.2)
    })

    infoLabel.setText(s"[${ss.version}] ${ss.info}")
    infoTooltip.text = s"Updated at ${ss.zonedTime}"
    if (ss.error.nonEmpty)
      updateErrorLabel(ss.error)
  }

  def updateErrorLabel(errorMsg: String): Unit = {
    if (errorMsg.nonEmpty) {
      errorLabel.text = errorMsg
      errorTooltip.text = errorMsg
      errorLabel.visible = true
    } else
      errorLabel.visible = false
  }
}
