package cc.theurgist.motrack.ui.config

import cc.theurgist.motrack.lib.config.ApplicationConfig

object ClientConfig {
  private val c = ApplicationConfig.branch("client")

  val ip: String = c.read("ip")
  val port: Int = c.read("port")
  val protocolVersion: String = c.read("protocolVersion")
}


