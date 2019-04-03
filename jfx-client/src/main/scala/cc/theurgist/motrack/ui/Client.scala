package cc.theurgist.motrack.ui

import com.typesafe.scalalogging.StrictLogging
import scalafx.application.JFXApp
import scalafxml.core.{FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color.LightGreen

object Client extends JFXApp with StrictLogging {
  Thread.currentThread().setName("JFX")
  logger.info("Motrack client has been started")


  val fxml: jfxs.Parent = FXMLView(getClass.getResource("/jfx/main.fxml"), NoDependencyResolver)


  stage = new PrimaryStage {
    title = "Motrack client!"

    minWidth = 400
    minHeight = 300

    scene = new Scene {
      fill = LightGreen
      root = fxml
    }
  }

  logger.info("JavaFX scene constructed")
}
