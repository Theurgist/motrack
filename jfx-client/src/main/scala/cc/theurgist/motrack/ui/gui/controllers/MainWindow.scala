package cc.theurgist.motrack.ui.gui.controllers

import cc.theurgist.motrack.ui.actors.command.CommandInterface
import com.typesafe.scalalogging.StrictLogging
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.HBox
import scalafxml.core.macros.sfxml

@sfxml
class MainWindow
(
    ci: CommandInterface,
    btnTest: Button,
    statusBar: HBox
) extends StrictLogging {
  logger.info("awakens: MainWindow")

  def btnTestClick(event: MouseEvent): Unit = {
    btnTest.setText("CLICKIN")
    ci.updateServerStatus()
  }
}