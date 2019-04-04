package cc.theurgist.motrack.ui

import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.scalalogging.StrictLogging
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContextExecutor

object Client extends IOApp with StrictLogging {

  implicit val system: ActorSystem                        = ActorSystem("Mo2")
  implicit val materializer: Materializer                 = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  Thread.currentThread().setName("JFX")
  logger.info("Motrack client has been started")

  override def run(args: List[String]): IO[ExitCode] = {
//    for {
//      _ <- UiApp.stage
//    } yield ExitCode.Success

    ???
  }

}
