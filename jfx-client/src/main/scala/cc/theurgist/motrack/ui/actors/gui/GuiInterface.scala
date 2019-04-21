package cc.theurgist.motrack.ui.actors.gui

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.{ContextShift, IO, Timer}
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.config.ClientConfig
import cc.theurgist.motrack.ui.gui.GuiApp
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._
import scala.language.postfixOps

//TODO Maybe redundant
class GuiInterface private (val actor: ActorRef) extends StrictLogging {
  logger.debug(s"UiInterface's actor: $actor")

  def closeApp(): Unit = actor ! "close"

}

object GuiInterface extends StrictLogging {

  def init(name: String, commandInterface: CommandInterface, timer: Timer[IO])(implicit system: ActorSystem,
                                                                               cs: ContextShift[IO]): IO[GuiInterface] = {

    def ping(uiActor: ActorRef, ci: CommandInterface, no: Long = 1): IO[Nothing] =
      for {
        _   <- IO(logger.trace(s"Ping №$no"))
        dur <- IO(FiniteDuration(ClientConfig.hostAutoping.toNanos, TimeUnit.NANOSECONDS))
        _   <- timer.sleep(dur)
        _   <- IO(ci.updateServerStatus())
        res <- ping(uiActor, ci, no + 1)
      } yield res

    for {
      gui <- IO(new GuiApp)
      ref <- IO(system.actorOf(GuiActor.props(gui.ctlrMain), name))
      res <- IO(new GuiInterface(ref))
      ci  <- IO(commandInterface.lend(ref))
      _   <- IO(gui.run(ci)(system.dispatcher))
      _   <- ping(ref, ci).start
    } yield res
  }
}
