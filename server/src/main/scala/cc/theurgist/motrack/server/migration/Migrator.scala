package cc.theurgist.motrack.server.migration

import com.typesafe.scalalogging.StrictLogging
import javax.sql.DataSource
import org.flywaydb.core.Flyway

/**
  * Machinery for database migrations application
  *
  * @param ds database connection settings
  */
class Migrator(ds: DataSource) extends StrictLogging {

  private val flyway: Flyway = {
    val c = Flyway.configure.dataSource(ds).load
    logger debug s"DB connection for '${ds.toString}' has been set up"
    c
  }

  /**
    * Apply migrations
    * Before applying migrations to in-memory database, make sure that there is already an open connection
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
