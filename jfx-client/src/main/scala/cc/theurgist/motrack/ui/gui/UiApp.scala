package cc.theurgist.motrack.ui.gui

import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.gui.controllers.MainWindowActions
import com.typesafe.scalalogging.StrictLogging
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.paint.Color.LightGreen
import scalafxml.core.{ExplicitDependencies, FXMLLoader, FXMLView, NoDependencyResolver}

import scala.concurrent.{ExecutionContextExecutor, Future}

class UiApp extends StrictLogging {

  var ctlrMain: Option[MainWindowActions] = None

  def run(ci: CommandInterface)(implicit ece: ExecutionContextExecutor): Future[Unit] = {
    class UiAppLauncher extends JFXApp with StrictLogging {
      Thread.currentThread().setName("JFX-ui")
      logger.info("Motrack UI has been started")

      val (fxmlRoot: jfxs.Parent, rootController: MainWindowActions) = {
        val loader = new FXMLLoader(getClass.getResource("/jfx/main.fxml"), new ExplicitDependencies(Map("ci" -> ci)))
        loader.load()
        (loader.getRoot[jfxs.Parent](), loader.getController[MainWindowActions]())
      }
      val fxmlSSLabel: jfxs.Parent =
        FXMLView(getClass.getResource("/jfx/controls/serverStatusLabel.fxml"), NoDependencyResolver)
      ctlrMain = Some(rootController)

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

      override def stopApp(): Unit = {
        ci.exit()
        Platform.exit()
        super.stopApp()
      }
    }

    Future { new UiAppLauncher().main(Array()) }(ece)
  }

}
