package cc.theurgist.motrack.ui.gui.controllers

import com.typesafe.scalalogging.StrictLogging
import scalafx.scene.control.Label
import scalafx.scene.input.MouseEvent
import scalafx.scene.shape.Circle
import scalafxml.core.macros.sfxml

trait MainHeaderController {
  def setUsername(s: String): Unit
  def setTotalBalance(s: String): Unit
  def doLogoff(e: MouseEvent): Unit

  var logoffAction: Unit => Unit = (_ => ())
}

@sfxml
class FxMainHeaderController(
    val avatar: Circle,
    val totalBalance: Label,
    val username: Label,
) extends MainHeaderController with StrictLogging {
  logger.trace("awakens: FxServerStatusLabel")

  def doLogoff(e: MouseEvent): Unit = logoffAction()

  def setUsername(s: String): Unit = username.setText(s)
  def setTotalBalance(s: String): Unit = totalBalance.setText(s)
  //def setAvatar(s: String): Unit = avatar.set

}
