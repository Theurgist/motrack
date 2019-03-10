package cc.theurgist.migration

import cc.theurgist.config.DbConfig
import com.typesafe.scalalogging.StrictLogging
import org.flywaydb.core.Flyway

/**
  * Machinery for database migrations application
  * @param cfg database connection settings
  */
class Migrator(cfg: DbConfig) extends StrictLogging {

  private val flyway: Flyway = {
    val c = Flyway.configure
      .dataSource(
        cfg.url,
        null, //"sa",
        null
      )
      .load
    logger info s"DB connection for '${cfg.name}' has been set up"
    c
  }

  /**
    * Apply migrations
    *
    * @return number of successfuly applied migrations
    */
  def migrate(setBaseline: Boolean): Int = {
    if (setBaseline) {
      Flyway.configure().baselineVersion("1")
      flyway.baseline()
    }
    val n = flyway.migrate()
    logger info s"$n migrations have been applied"
    n
  }

}
