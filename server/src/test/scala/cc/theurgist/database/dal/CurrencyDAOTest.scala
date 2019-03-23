package cc.theurgist.database.dal

import cc.theurgist.database.Db
import cc.theurgist.model.Currency
import cc.theurgist.testharness.InmemDbSetup
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class CurrencyDAOTest extends WordSpec with BeforeAndAfterEach with Matchers with InmemDbSetup {
  val dao = new CurrencyDAO(Db.getInmemCtx.get)

  "Currencies" should {
    "being inserted" in {
      val cs = List(
        Currency(0, "rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
        Currency(0, "usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
        Currency(0, "eur", "Euro", "€", Option("Europe"), isCrypto = false),
      )
      val newId = dao.insert(cs.head)
      val insertedHead = dao.findByCode(cs.head.code).head
      insertedHead.id should be(newId)
      insertedHead.unicode should be("\u20BD")

      dao.insert(cs.tail).length should be(2)
      dao.findByCode(cs(2).code).head.unicode should be("€")

      //val updating = cs(2).copy(name = "Tugrik")
      //dao.insertOrUpdateThroughMacro(updating)
      //dao.findByCode(cs(2).code).head.name should be("Tugrik")

    }
  }

  override def beforeEach() {}

  override def afterEach() {}
}
