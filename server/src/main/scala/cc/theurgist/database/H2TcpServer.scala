package cc.theurgist.database

import java.sql.{Connection, DriverManager}

import cc.theurgist.config.DbConfig
import com.typesafe.scalalogging.StrictLogging
import org.h2.tools.Server

/**
  * Static runner for H2 interprocess TCP connection support
  */
object H2TcpServer extends StrictLogging {
  private val server: Server = Server.createTcpServer()

  def start(): Unit = {
    if (!server.isRunning(true)) {
      server.start()
      logger.info("Started H2 inmem TCP server")
    }
  }

  def stop(): Unit = {
    if (server.isRunning(true)) {
      server.stop()
      logger.info("Stopped H2 inmem TCP server")
    }
  }

  def isRunning: Boolean = {
    server.isRunning(true)
  }

  def getInmemConnection: Option[Connection] = {
    DbConfig.inmem match {
      case Some(cfg) =>
        Class.forName(cfg.driver)
        val conn: Connection = DriverManager.getConnection(cfg.url)
        start()
        if (isRunning) {
          logger.info(s"URL: jdbc:h2:${server.getURL}/${cfg.url.replace("jdbc:h2:","")}")
          Some(conn)
        } else {
          logger.error("Failed to start TCP server connection")
          conn.close()
          None
        }

//        conn.createStatement.execute("create table test(id int)")
//        System.out.println("Press [Enter] to stop.")
//        System.in.read
      case None =>
        logger.error(s"Failed to retrieve config for H2 static server")
        None
    }
  }

}
