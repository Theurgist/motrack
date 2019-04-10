package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db
import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.lib.testharness.DataGenerators._
import cc.theurgist.motrack.server.testharness.InmemDbSetup
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class TransactionDAOTest extends WordSpec with BeforeAndAfterEach with Matchers with InmemDbSetup {
  implicit val ctx: InmemContext = Db.getInmemCtx.get
  val dao = new TransactionDAO(ctx)
  val udao = new UserDAO
  val cdao = new CurrencyDAO(ctx)
  val adao = new AccountDAO(ctx)

  "Transactions basic sums" should {
    "calculate right" in {
      val u = {
        val u = genUsers(1).head
        u.copy(id = udao.insert(u))
      }
      val c = {
        val c = genCurrencies(1).head
        c.copy(id = cdao.insert(c))
      }

      val (a1, a2) = {
        val accs = genAccounts(List((2,u)), c)
        val (a1, a2) = (accs.head.copy(ownerId = u.id), accs.tail.head.copy(ownerId = u.id))
        (a1.copy(id = adao.insert(a1)), a2.copy(id = adao.insert(a2)))
      }

      val tx1 = genTransactions(10, a1, a2)
      val tx2 = genTransactions(20, a2, a1)
      dao.insert(tx1)
      dao.insert(tx2)

      val balance = dao.calcBalance(a1.id)
      val outcome = tx1.map(t => t.amount).sum
      val income = tx2.map(t => t.amount * t.conversionRate).sum
      balance shouldBe(income - outcome)
    }
  }

  override def beforeEach() {}

  override def afterEach() {}
}
