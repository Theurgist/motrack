package cc.theurgist.motrack.ui.gui.controllers

import cats.effect.IO
import cc.theurgist.motrack.lib.Timing
import cc.theurgist.motrack.lib.dto.ServerStatus
import cc.theurgist.motrack.lib.model.account.{Account, AccountId, BankAccount}
import cc.theurgist.motrack.lib.model.currency.CurrencyId
import cc.theurgist.motrack.lib.model.security.user.UserId
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import com.typesafe.scalalogging.StrictLogging
import scalafx.beans.value.ObservableValue
import scalafx.collections.ObservableBuffer
import scalafx.scene.Cursor
import scalafx.scene.control.{Button, ListCell, ListView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafxml.core.macros.{nested, sfxml}

import scala.collection.mutable

trait MainWindowController {
  def updateServerStatus(ss: ServerStatus): Unit
  def updateErrorLabel(errorMsg: String): Unit

  def exit(): Unit

  def btnTestClick(event: MouseEvent): Unit

  def ssLabelController: ServerStatusLabelController
  def mainHeaderController: MainHeaderController
  def loginPageController: LoginPageController
}

@sfxml
class FxMainWindowController(
    ci: CommandInterface,
    btnTest: Button,
    statusBar: HBox,
    accountsList: ListView[Account],
    @nested[FxServerStatusLabelController] val ssLabelController: ServerStatusLabelController,
    @nested[FxMainHeaderController] val mainHeaderController: MainHeaderController,
    @nested[FxLoginPageController] val loginPageController: LoginPageController,
) extends HBox with MainWindowController with StrictLogging {
  logger.trace("awakens: MainWindow")

  loginPageController.loginAction = (u: String, p: String) => ci.login(u,p)


  val accounts: mutable.MutableList[Account] =
    mutable.MutableList(Account(new AccountId(1), new UserId(3), new CurrencyId(3), "TTTR", BankAccount, Timing.now))
  val buf = ObservableBuffer(Seq[Account]())
  accountsList.items = buf

  def initScene(): Unit = {

    accountsList.cellFactory = { view =>
      {
        new ListCell[Account]() {
          textFill = Color.Blue
          cursor = Cursor.Hand
          item.onChange { (obs: ObservableValue[Account, Account], v1: Account, v2: Account) =>
            if (v2 != null) {

              graphic = new Circle {
                fill = Color.Green
                radius = 8
              }
              graphicTextGap = 1.0
              text = v2.name
            }
          }
        }
      }
    }
  }
  initScene()

  def btnTestClick(event: MouseEvent): Unit = {
    btnTest.setText("CLICKIN")
    ci.updateServerStatus()
    //initScene()
    updateAccounts(
      accounts ++ Seq(Account(new AccountId(1), new UserId(1), new CurrencyId(3), "ZZHA", BankAccount, Timing.now))
    )
  }

  def updateServerStatus(ss: ServerStatus): Unit = ssLabelController.updateServerStatus(ss)
  def updateErrorLabel(errorMsg: String): Unit   = ssLabelController.updateErrorLabel(errorMsg)

  def updateAccounts(xs: Seq[Account]): Unit = {
    buf.addAll(xs: _*)
  }

  def exit(): Unit = {
    ci.exit()
  }
}
