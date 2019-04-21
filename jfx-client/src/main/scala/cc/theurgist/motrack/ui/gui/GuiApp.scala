package cc.theurgist.motrack.ui.gui

import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.gui.controllers.MainWindowController
import com.typesafe.scalalogging.StrictLogging
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.paint.Color.LightGreen
import scalafxml.core.{ExplicitDependencies, FXMLLoader}

import scala.concurrent.{ExecutionContextExecutor, Future}

class GuiApp extends StrictLogging {

  //TODO eliminate var
  /**
    * Main GUI controller
    */
  var ctlrMain: Option[MainWindowController] = None

  /**
    * GUI application execution starting point
    *
    * @param ci main commanding interface for application reactions
    * @param ece context for result future generation
    * @return
    */
  def run(ci: CommandInterface)(implicit ece: ExecutionContextExecutor): Future[Unit] =
    Future {
      class GuiAppLauncher extends JFXApp with StrictLogging {
        Thread.currentThread().setName("JFX-ui")

        val (fxmlRoot, rootController) = loadJfxml(ci)
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
        logger.info("JavaFX scene has been constructed")

        override def stopApp(): Unit = {
          ci.exit()
          Platform.exit()
          super.stopApp()
        }
      }

      new GuiAppLauncher().main(Array())
    }(ece)

  def loadJfxml(ci: CommandInterface): (jfxs.Parent, MainWindowController) = {
    try {
      val loader = new FXMLLoader(
        getClass.getResource("/jfx/main.fxml"),
        new ExplicitDependencies(Map("ci" -> ci))
      )
      loader.load()
      (loader.getRoot[jfxs.Parent](), loader.getController[MainWindowController]())
    } catch {
      case e: Exception =>
        logger.error(s"Failure at application initialization: $e")
        throw e
    }
  }

}
