package cc.theurgist

import java.sql.Connection

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp}
import cc.theurgist.config.SrvConfig
import cc.theurgist.database.{Db, InmemTcpServer}
import cc.theurgist.migration.Migrator
import cc.theurgist.routes.Root
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContextExecutor

object Server extends IOApp with StrictLogging {
  implicit val system: ActorSystem                        = ActorSystem("Mo2")
  implicit val materializer: Materializer                 = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val conn: Connection = InmemTcpServer.getInmemConnection.get
  new Migrator(Db.inmemDs.get).migrate(true)

  Thread.currentThread().setName("SRV")

  def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- {
        logger.info("Motrack server has been started")
        IO.fromFuture(IO(Http().bindAndHandle(new Root()(), SrvConfig.ip, SrvConfig.port)))
      }
    } yield ExitCode.Success
  }
}
