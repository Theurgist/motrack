package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.lib.model.currency.{Currency, CurrencyId}
import cc.theurgist.motrack.server.testharness.InmemDbSetup
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class CurrencyDAOTest extends WordSpec with BeforeAndAfterEach with Matchers with InmemDbSetup {
  val dao = new CurrencyDAO(Db.getInmemCtx.get)

  "Currencies" should {
    "being inserted" in {
      val cs = List(
        Currency(CurrencyId.none, "C1", "NAME1", "\u20BD", Option("SOME"), isCrypto = false),
        Currency(CurrencyId.none, "C2", "NAME2", "$", Option("COUNTRY"), isCrypto = false),
        Currency(CurrencyId.none, "C3", "NAME3", "€", None, isCrypto = true),
      )
      val newId        = dao.insert(cs.head)
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
