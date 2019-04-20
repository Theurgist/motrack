package cc.theurgist.motrack.ui.actors.gui

import akka.actor.{ActorRef, ActorSystem}
import cats.effect.{ContextShift, IO, Timer}
import cc.theurgist.motrack.ui.actors.command.CommandInterface
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._
import scala.language.postfixOps

class GuiInterface private (val actor: ActorRef) extends StrictLogging {
  logger.trace(s"UiInterface's actor: $actor")

  def closeApp(): Unit = actor ! "close"

}

object GuiInterface {

  def init(name: String, commandInterface: CommandInterface, timer: Timer[IO])(implicit system: ActorSystem,
                                                                               cs: ContextShift[IO]): IO[GuiInterface] = {

    def ping(uiActor: ActorRef, ci: CommandInterface,no: Long = 1): IO[Nothing] =
      for {
        _   <- IO(println(s"Ping №$no"))
        _   <- timer.sleep(3 seconds)
        _   <- IO(ci.updateServerStatus())
        res <- ping(uiActor, ci, no + 1)
      } yield res

    for {
      ref <- IO(system.actorOf(GuiActor.props, name))
      ci <- IO(commandInterface.lend(ref))
      _   <- ping(ref, ci).start
      res <- IO(new GuiInterface(ref))
    } yield res
  }
}