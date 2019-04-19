package cc.theurgist.motrack.ui.gui.controllers

import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.HBox
import scalafxml.core.macros.sfxml

@sfxml
class MainWindow
(
    btnTest: Button,
    statusBar: HBox
) {

  def btnTestClick(event: MouseEvent): Unit = {
    btnTest.setText("CLICKIN")
  }
}
