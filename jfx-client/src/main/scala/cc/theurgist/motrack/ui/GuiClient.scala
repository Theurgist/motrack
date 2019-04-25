package cc.theurgist.motrack.ui

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp, Timer}
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.actors.gui.GuiInterface
import com.typesafe.scalalogging.StrictLogging

/**
  * Starting point for Gui application
  */
object GuiClient extends IOApp with StrictLogging {
  Thread.currentThread().setName("Client")

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      implicit0(sys: ActorSystem) <- IO(ActorSystem("Mo2"))
      implicit0(m: Materializer)  <- IO(ActorMaterializer())
      commandIface                <- CommandInterface.init("CMD")
      _                           <- GuiInterface.init("GUI", commandIface, Timer[IO])
      _                           <- IO(logger.info("Motrack client has been started"))

    } yield ExitCode.Success
  }

}
