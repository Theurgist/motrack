package cc.theurgist.motrack.ui.actors.gui

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.{ContextShift, ExitCode, IO, Timer}
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import cc.theurgist.motrack.ui.config.ClientConfig
import cc.theurgist.motrack.ui.gui.GuiApp
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Graphical user interface initialization entry point
  */
object GuiInterface extends StrictLogging {

  /**
    * Initialize GUI with interaction system with enabled server autopinger
    *
    * @param name new actor's name
    * @param commandInterface actions core handler
    * @param timer IO timer instance
    * @param system actor system
    * @param cs IO context shift object
    * @return meaningless result
    */
  def init(name: String, commandInterface: CommandInterface, timer: Timer[IO])(implicit system: ActorSystem,
                                                                               cs: ContextShift[IO]): IO[ExitCode] = {

    def autoPing(uiActor: ActorRef, ci: CommandInterface, no: Long = 1): IO[Nothing] =
      for {
        _   <- IO(logger.trace(s"Ping №$no"))
        dur <- IO(FiniteDuration(ClientConfig.hostAutoping.toNanos, TimeUnit.NANOSECONDS))
        _   <- IO(ci.updateServerStatus())
        _   <- timer.sleep(dur)
        res <- autoPing(uiActor, ci, no + 1)
      } yield res

    for {
      gui <- IO(new GuiApp)
      ref <- IO(system.actorOf(GuiActor.props(gui.ctlrMain), name))
      ci  <- IO(commandInterface.lend(ref))
      _   <- IO(gui.run(ci)(system.dispatcher))
      _   <- timer.sleep(3 seconds)
      _   <- autoPing(ref, ci).start
    } yield ExitCode.Success
  }
}
