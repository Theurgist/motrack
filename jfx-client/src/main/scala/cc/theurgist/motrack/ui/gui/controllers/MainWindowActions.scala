package cc.theurgist.motrack.ui.gui.controllers

import scalafx.scene.input.MouseEvent

trait MainWindowActions {

  def exit(): Unit

  def btnTestClick(event: MouseEvent): Unit

  def updateServerStatus(): Unit
}
