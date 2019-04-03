package cc.theurgist.motrack.server

import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.migration.Migrator
import org.scalatest.{Matchers, WordSpec}

class FlywaySetupTest extends WordSpec with Matchers {

  "h2 database" should {

    "be able to setup" in {
      // Before applying migrations, we should open a connection first
      val conn = Db.getInmemConnection
      val m = new Migrator(Db.inmemDs.get)
      val cnt = m.migrate(true)
      cnt should be >= 0
      conn.foreach(_.close())
    }

    "support simple queries" in {
      val conn = Db.getInmemConnection.get                  // This snippet has been moved to InmemDbSetup trait
      new Migrator(Db.inmemDs.get).migrate(true)  //

      conn.prepareStatement("""
                              | insert into currencies values(
                              | null, 'TeST', 'TestCurrency', '₱', 'TestCountry', 'false')
                              | """.stripMargin).execute()

      val rSize = conn.prepareStatement("select count(*) from currencies where CODE = 'TeST'").executeQuery()
      rSize.first()
      rSize.getInt("COUNT(*)") should be(1)

      val rData = conn.prepareStatement("select * from currencies where CODE = 'TeST'").executeQuery()
      rData.first()
      rData.getString("code") should be("TeST")
      // System.out.println("Press [Enter] to stop.")
      // System.in.read
      conn.close()
    }

  }

}
