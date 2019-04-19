package cc.theurgist.motrack.ui

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp, Timer}
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.actors.gui.GuiInterface
import cc.theurgist.motrack.ui.gui.UiApp
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future

object Client extends IOApp with StrictLogging {

  def system: IO[ActorSystem] = IO(ActorSystem("Mo2"))

  def runAll: IO[Unit] =
    for {
      r <- IO(println("gege"))
    } yield r

  Thread.currentThread().setName("JFX")
  logger.info("Motrack client has been started")

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      implicit0(sys: ActorSystem) <- system
      implicit0(m: Materializer)  <- IO(ActorMaterializer())
      commandIface                <- CommandInterface.init("CMD")
      guiIface                    <- GuiInterface.init("GUI", commandIface, Timer[IO])
      _                           <- IO(commandIface.updateServerStatus(guiIface.actor))
      gui                         <- IO(Future { new UiApp().main(Array()) }(sys.dispatcher))

    } yield ExitCode.Success
  }

}
