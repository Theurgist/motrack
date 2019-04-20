package cc.theurgist.motrack.ui.gui

import cc.theurgist.motrack.ui.actors.command.CommandInterface
import com.typesafe.scalalogging.StrictLogging
import scalafx.application.JFXApp
import scalafxml.core.{ExplicitDependencies, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color.LightGreen

class UiApp(ci: CommandInterface) extends JFXApp with StrictLogging {
  Thread.currentThread().setName("JFX-ui")
  logger.info("Motrack UI has been started")

  val fxmlRoot: jfxs.Parent =FXMLView(
      getClass.getResource("/jfx/main.fxml"),
      new ExplicitDependencies(Map("ci" -> ci)))
  val fxmlSSLabel: jfxs.Parent = FXMLView(getClass.getResource("/jfx/controls/serverStatusLabel.fxml"), NoDependencyResolver)

  stage = new PrimaryStage {
    title = "Motrack client!"

    minWidth = 400
    minHeight = 300

    scene = new Scene {
      fill = LightGreen
      root = fxmlRoot
    }
  }

  logger.info("JavaFX scene constructed")
}
