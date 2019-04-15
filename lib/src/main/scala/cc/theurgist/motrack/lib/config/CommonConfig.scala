package cc.theurgist.motrack.lib.config

object CommonConfig {
  private val c = ApplicationConfig.branch("motrack")

  val protocolVersion: String = c.read("protocolVersion")
}


