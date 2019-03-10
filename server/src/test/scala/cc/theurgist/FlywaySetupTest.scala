package cc.theurgist
import cc.theurgist.config.DbConfig
import cc.theurgist.database.H2TcpServer
import cc.theurgist.migration.Migrator
import org.scalatest.WordSpec

class FlywaySetupTest extends WordSpec {

  "h2 database" should {

    "be able to setup" in {
      val conn = H2TcpServer.getInmemConnection.get

      val m = new Migrator(DbConfig.inmem.get)
      m.migrate(true)

      System.out.println("Press [Enter] to stop.")
      System.in.read
    }

  }

}
