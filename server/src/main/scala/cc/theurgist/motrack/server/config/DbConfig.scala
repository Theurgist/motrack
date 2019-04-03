package cc.theurgist.motrack.server.config

import cc.theurgist.motrack.lib.config.{ApplicationConfig, ConfigBranch}

/**
  * Settings for database connection
  *
  * @param configName path of configuration in conf file
  * @param url connection string's URL
  * @param driver FQN for java driver class
  * @param user authority name
  * @param password secret string
  */
case class DbConfig(
    configName: String,
    url: String,
    driver: String,
    user: String,
    password: String
)

object DbConfig {

  /**
    * Predefined connection for in-memory fast storage
    */
  val inmem = DbConfig(ApplicationConfig.branch("db.inmem"))

  /**
    * Predefined connection for main storage
    */
  val persistent = DbConfig(ApplicationConfig.branch("db.persistent"))

  private def apply(c: ConfigBranch): Option[DbConfig] = {
    for (url    <- c.opt[String]("url");
         driver <- c.opt[String]("driver"))
      yield new DbConfig(c.path, url, driver, c.readNullable[String]("user"), c.readNullable[String]("password"))
  }
}
