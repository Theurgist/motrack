package cc.theurgist.database

import org.h2.tools.Server
import java.sql.Connection
import java.sql.DriverManager

import cc.theurgist.config.{ApplicationConfig, DbConfig}
import com.typesafe.config.ConfigFactory

/**
  *
  */
object H2StaticServer extends App {
  val conf = ConfigFactory.load()
  val url = conf.getString("h2mem1.url")

  val cfg = DbConfig(ApplicationConfig.branch("h2mem1"))

  Class.forName("org.h2.Driver")
  val conn: Connection = DriverManager.getConnection("jdbc:h2:mem:test")
  conn.createStatement.execute("create table test(id int)")

  // start a TCP server
  // (either before or after opening the database)
  val server: Server = Server.createTcpServer().start

  // .. use in embedded mode ..

  // or use it from another process:
  System.out.println("Server started and connection is open.")
  System.out.println("URL: jdbc:h2:" + server.getURL + "/mem:test")

  // now start the H2 Console here or in another process using
  // java org.h2.tools.Console -web -browser

  System.out.println("Press [Enter] to stop.")
  System.in.read

  System.out.println("Stopping server and closing the connection")
  server.stop()
}
