package cc.theurgist.motrack.ui.gui.controllers

import com.typesafe.scalalogging.StrictLogging
import scalafx.scene.control.{Label, PasswordField, TextField}
import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

trait LoginPageController {
  def doLogin(): Unit
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
) extends VBox with LoginPageController with StrictLogging {
  logger.trace("awakens: FxLoginPageController")

  //def doLogin(e: MouseEvent): Unit = loginAction.foreach(f => f(username.text, password.text))
  def doLogin(): Unit =
    loginAction(username.getText, password.getText)

  def updateError(msg: String): Unit = error.text = msg

  def reset(lastLogin: String): Unit = {
    username.text = lastLogin
    error.text = ""
    password.text = ""
  }
}
