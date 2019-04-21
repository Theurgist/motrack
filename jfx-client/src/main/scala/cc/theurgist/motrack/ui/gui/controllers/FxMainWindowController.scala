package cc.theurgist.motrack.ui.gui.controllers

import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import com.typesafe.scalalogging.StrictLogging
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.HBox
import scalafxml.core.macros.{nested, sfxml}

trait MainWindowController {
  def updateServerStatus(ss: ServerStatus): Unit
  def exit(): Unit

  def btnTestClick(event: MouseEvent): Unit
}

@sfxml
class FxMainWindowController
(
    ci: CommandInterface,
    btnTest: Button,
    statusBar: HBox,
    @nested[FxServerStatusLabelController] ssLabelController: ServerStatusLabelController
) extends MainWindowController with StrictLogging {
  logger.trace("awakens: MainWindow")

  def btnTestClick(event: MouseEvent): Unit = {
    btnTest.setText("CLICKIN")
    ci.updateServerStatus()
  }

  def updateServerStatus(ss: ServerStatus): Unit = ssLabelController.updateServerStatus(ss)


  def exit(): Unit = {
    ci.exit()
  }
}
