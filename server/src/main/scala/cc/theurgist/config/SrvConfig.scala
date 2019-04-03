package cc.theurgist.config

object SrvConfig {
  private val c = ApplicationConfig.branch("server")

  val ip: String = c.read("ip")
  val port: Int = c.read("port")
}


