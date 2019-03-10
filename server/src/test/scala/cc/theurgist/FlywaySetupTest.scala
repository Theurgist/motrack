package cc.theurgist
import cc.theurgist.config.DbConfig
import cc.theurgist.database.{Db, InmemTcpServer}
import cc.theurgist.migration.Migrator
import org.scalatest.{Matchers, WordSpec}

class FlywaySetupTest extends WordSpec with Matchers {

  "h2 database" should {

    "be able to setup" in {
      // Before applying migrations, we should open a connection first
      Db.openInmem

      val m = new Migrator(DbConfig.inmem.get)
      m.migrate(true) should be > 0

    }

    "support simple queries" in {
      val conn = InmemTcpServer.getInmemConnection.get
      conn.prepareStatement("""
                              | insert into currency values(
                              | 'TeST', 'TestCurrency', '₱', 'TestCountry', 'false')
                              | """.stripMargin).execute()

//      Currency("rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
//      Currency("usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
//      Currency("eur", "Euro", "€", Option("Europe"), isCrypto = false),

      val rSize = conn.prepareStatement("select count(*) from currency where CODE = 'TeST'").executeQuery()
      rSize.first()
      rSize.getInt("COUNT(*)") should be(1)

      val rData = conn.prepareStatement("select * from currency where CODE = 'TeST'").executeQuery()
      rData.first()
      rData.getString("code") should be("TeST")
      // System.out.println("Press [Enter] to stop.")
      // System.in.read
    }

  }

}
