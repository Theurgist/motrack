package cc.theurgist.motrack.ui.config

import java.time.Duration

import cc.theurgist.motrack.lib.config.ApplicationConfig

object ClientConfig {
  private val c = ApplicationConfig.branch("motrack.client")

  val ip: String = c.read("ip")
  val port: Int = c.read("port")
  val hostAutoping: Duration = Duration.parse(c.read("hostAutoping"))
  val apiRoot: String = c.read("apiRoot")

  val hostHttpPath = s"http://$ip:$port/"
  val hostApiPath =  s"http://$ip:$port/$apiRoot/"
  def hostApiPath(relativePath: String): String = s"$hostApiPath$relativePath"
}


