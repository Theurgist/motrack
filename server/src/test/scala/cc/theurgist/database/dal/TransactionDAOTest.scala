package cc.theurgist.database.dal

import cc.theurgist.database.Db
import cc.theurgist.testharness.InmemDbSetup
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class TransactionDAOTest extends WordSpec with BeforeAndAfterEach with Matchers with InmemDbSetup {
  val dao = new TransactionDAO(Db.getInmemCtx.get)

  "Transactions basic sums" should {
    "calculate right" in {


    }
  }

  override def beforeEach() {}

  override def afterEach() {}
}
