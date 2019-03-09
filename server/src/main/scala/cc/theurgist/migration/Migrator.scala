package cc.theurgist.migration

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.StrictLogging
import org.flywaydb.core.Flyway


class Migrator extends StrictLogging {

  val conf = ConfigFactory.load()
  val url = conf.getString("h2mem1.url")

  val flyway = Flyway.configure.dataSource(
    url,
    "sa",
    null
  ).load
  logger.info(s"DB connection for '$url' has been set up")

  // Start the migration
  flyway.migrate()
  logger info "Migrations have been applied"

}
