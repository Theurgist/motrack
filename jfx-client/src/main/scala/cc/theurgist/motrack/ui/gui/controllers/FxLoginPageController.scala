package cc.theurgist.motrack.ui.gui.controllers

import com.typesafe.scalalogging.StrictLogging
import scalafx.scene.control.{Label, PasswordField, TextField}
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml

trait LoginPageController {
  def doLogin(e: MouseEvent): Unit
  def updateError(msg: String): Unit
  def reset(lastLogin: String): Unit

  /**
    * Callback to controller method
    */
  var loginAction: (String, String) => Unit = (_,_) => ()
}

@sfxml
class FxLoginPageController(
    val username: TextField,
    val password: PasswordField,
    val error: Label,
) extends LoginPageController with StrictLogging {
  logger.trace("awakens: FxLoginPageController")

  def doLogin(e: MouseEvent): Unit = {

  }

  def updateError(msg: String): Unit = error.text = msg

  def reset(lastLogin: String): Unit = {
    username.text = lastLogin
    error.text = ""
    password.text = ""
  }
}
