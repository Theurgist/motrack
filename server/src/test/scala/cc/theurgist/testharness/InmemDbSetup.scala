package cc.theurgist.testharness

import cc.theurgist.database.Db
import cc.theurgist.migration.Migrator
import org.scalatest.{BeforeAndAfterAll, TestSuite}

/**
  * Gets in-memory database ready by applying migrations
  */
trait InmemDbSetup extends TestSuite with BeforeAndAfterAll {
  // Before applying migrations, we should open a connection first
  private val conn = Db.getInmemConnection.get

  override def beforeAll(): Unit = {
    new Migrator(Db.inmemDs.get).migrate(true)
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    conn.close()
  }
}
