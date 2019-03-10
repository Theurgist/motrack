package cc.theurgist.config

sealed class DbConfig(
    val name: String,
    val url: String,
    val driver: String
)

object DbConfig {
  val inmem      = DbConfig(ApplicationConfig.branch("db.inmem"))
  val persistent = DbConfig(ApplicationConfig.branch("db.persistent"))

  private def apply(c: ConfigBranch): Option[DbConfig] = {
    for (url    <- c.opt[String]("url");
         driver <- c.opt[String]("driver")) yield new DbConfig(c.path, url, driver)
  }
}
