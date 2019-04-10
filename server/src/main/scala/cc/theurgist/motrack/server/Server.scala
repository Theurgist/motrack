package cc.theurgist.motrack.server

import java.sql.Connection

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import cats.effect.{ExitCode, IO, IOApp}
import cc.theurgist.motrack.server.config.SrvConfig
import cc.theurgist.motrack.server.database.{Db, InmemTcpServer}
import cc.theurgist.motrack.server.migration.Migrator
import cc.theurgist.motrack.server.routes.ServerRootRoute
import com.softwaremill.session.javadsl.SessionSerializers
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContextExecutor

object Server extends IOApp with StrictLogging {
  implicit val system: ActorSystem                        = ActorSystem("Mo2")
  implicit val materializer: Materializer                 = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  //SessionSerializers.LongToStringSessionSerializer

  val conn: Connection = InmemTcpServer.getInmemConnection.get
  new Migrator(Db.inmemDs.get).migrate(true)

  Thread.currentThread().setName("SRV")

  def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- {
        logger.info(s"Motrack server has been started at ${SrvConfig.ip}:${SrvConfig.port}")
        IO.fromFuture(IO(Http().bindAndHandle(new ServerRootRoute().r, SrvConfig.ip, SrvConfig.port)))
      }
    } yield ExitCode.Success
  }
}
