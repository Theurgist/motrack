package cc.theurgist.motrack.ui

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp}
import cc.theurgist.motrack.ui.actors.command.CommandActor
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContextExecutor

object Client extends IOApp with StrictLogging {

  implicit val system: ActorSystem                        = ActorSystem("Mo2")
  implicit val materializer: Materializer                 = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  Thread.currentThread().setName("JFX")
  logger.info("Motrack client has been started")

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      commandActor <- IO(system.actorOf(CommandActor.props()), "CMD")
    } yield ExitCode.Success
  }

}
