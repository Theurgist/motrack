package cc.theurgist.database
import java.sql.Connection

import cc.theurgist.config.DbConfig
import cc.theurgist.quill.MacroQueries
import com.typesafe.scalalogging.StrictLogging
import com.zaxxer.hikari.HikariDataSource
import io.getquill.{H2JdbcContext, QueryProbing, SnakeCase}

/**
  * Database connections manager
  */
object Db extends StrictLogging {
  type InmemContext = H2JdbcContext[SnakeCase] with MacroQueries

  lazy val inmemDs: Option[HikariDataSource] = {
    DbConfig.inmem match {
      case Some(cfg) =>
        val ds = new HikariDataSource
        ds.setPoolName(s"HPool-${cfg.configName}")
        ds.setJdbcUrl(cfg.url)
        ds.setUsername(cfg.user)
        Some(ds)
      case None =>
        logger.error(s"Failed to retrieve config for in-memory database connection")
        None
    }
  }

  /**
    * Generate connection for inmem db
    *
    * @return
    */
  def getInmemConnection: Option[Connection] = {
    inmemDs match {
      case Some(ds) =>
        val conn: Connection = ds.getConnection()
        logger.debug("Generated inmem connection")
        Some(conn)
      case None =>
        logger.error(s"Failed to retrieve connection for in-memory database")
        None
    }
  }

  def getInmemCtx: Option[InmemContext] = {
    inmemDs match {
      case Some(ds) =>
        Some(new H2JdbcContext[SnakeCase](SnakeCase, ds) with MacroQueries)
      case None =>
        logger.error(s"Failed to retrieve quill context for in-memory database")
        None
    }
  }

  /**
    * Generate connection for persistent db
    *
    * @return
    */
  def getPersistentConnection: Option[Connection] = ???
}
