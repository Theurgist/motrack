package cc.theurgist.database
import java.sql.{Connection, DriverManager}

import cc.theurgist.config.DbConfig
import com.typesafe.scalalogging.StrictLogging

/**
  * Database connections manager
  */
object Db extends StrictLogging {

  /**
    * Generate connection for inmem db
    *
    * @return
    */
  def openInmem: Option[Connection] = {
    DbConfig.inmem match {
      case Some(cfg) =>
        Class.forName(cfg.driver)
        val conn: Connection = DriverManager.getConnection(cfg.url)
        logger.debug("Generated inmem connection")
        Some(conn)
      case None =>
        logger.error(s"Failed to retrieve config for H2 static server")
        None
    }
  }

  /**
    * Generate connection for persistent db
    *
    * @return
    */
  def openPersistent: Option[Connection] = ???
}
