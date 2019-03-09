package cc.theurgist.config

case class DbConfig(
    url: Option[String],
    driver: Option[String]
)



object DbConfig {

  def apply(c: ConfigBranch): DbConfig = {
    val url    = c.opt[String]("url")
    val driver = c.opt[String]("driver")
    DbConfig(url, driver)
  }
}
