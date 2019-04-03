package cc.theurgist.motrack.server.database

import java.sql.Connection

import cc.theurgist.motrack.server.config.DbConfig
import com.typesafe.scalalogging.StrictLogging
import org.h2.tools.Server

/**
  * Static runner for H2 interprocess TCP connection support
  */
object InmemTcpServer extends StrictLogging {
  private val server: Server = Server.createTcpServer()

  /**
    * Open connection to in-memory database and enable H2 TCP server
    *
    * @return optional connection object, if succeeded
    */
  def getInmemConnection: Option[Connection] = {
    Db.getInmemConnection match {
      case connOpt: Some[Connection] =>
        start()
        if (isRunning) {
          logger.info(s"Started H2 TCP server at: jdbc:h2:${server.getURL}/${DbConfig.inmem.get.url.replace("jdbc:h2:", "")}")
          connOpt
        } else {
          logger.error("Failed to start TCP server connection")
          connOpt.get.close()
          None
        }
        logger.info("Started H2 TCP server")
        connOpt
      case None =>
        None
    }

    // conn.createStatement.execute("create table test(id int)")
    // System.out.println("Press [Enter] to stop.")
    // System.in.read
  }

  /**
    * Start TCP server
    *
    * @return true if it is already running or just started, false - on some error
    */
  def start(): Boolean = {
    if (!isRunning) {
      server.start()
      logger.info("Started H2 inmem TCP server")
      isRunning
    } else
      true
  }

  /**
    * Check server status
    *
    * @return true if it is running, false - if not
    */
  def isRunning: Boolean = {
    server.isRunning(true)
  }

  /**
    * Stop TCP server
    *
    * @return true if it is already not running or just stopped, false - on some error
    */
  def stop(): Boolean = {
    if (isRunning) {
      server.stop()
      logger.info("Stopped H2 inmem TCP server")
      isRunning
    } else
      true
  }

}
