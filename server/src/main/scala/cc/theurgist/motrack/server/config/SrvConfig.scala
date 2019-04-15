package cc.theurgist.motrack.server.config

import cc.theurgist.motrack.lib.config.ApplicationConfig

object SrvConfig {
  private val c = ApplicationConfig.branch("motrack.server")

  val ip: String = c.read("ip")
  val port: Int = c.read("port")
  val secret: String = c.read("secret")
}


