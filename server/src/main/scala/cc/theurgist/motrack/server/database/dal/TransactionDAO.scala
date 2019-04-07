package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.lib.model.account.AccountId
import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.lib.model.transaction.{Transaction, TransactionId}
import com.typesafe.scalalogging.StrictLogging

class TransactionDAO(context: InmemContext) extends BaseCRUD[Transaction](context) with StrictLogging {

  import ctx._
  private val transactions = quote(querySchema[Transaction]("transactions"))

  def insert(o: Transaction): TransactionId =
    ctx.run { quote(transactions.insert(lift(o))).returning(_.id) }

  def insert(ox: Seq[Transaction]): List[TransactionId] = ctx.run {
    quote(liftQuery(ox).foreach(o => transactions.insert(o).returning(_.id)))
  }

  def calcBalance(aid: AccountId): Double = {
    //ctx.run (byAccId(aid).map(t => t.amount * t.conversionRate).sum).getOrElse(0.0)
    val inbound = ctx.run (byAccIdInbound(aid).map(t => t.amount * t.conversionRate).sum)
    val outbound = ctx.run (byAccIdOutbound(aid).map(t => t.amount).sum)
    inbound.getOrElse(0.0) - outbound.getOrElse(0.0)
  }

  def delete(id: TransactionId): Long = ctx.run { byId(id).delete }

  def find(id: TransactionId): Option[Transaction] = {
    extractUnique(ctx.run(quote { byId(id) }), s"id '$id'")
  }

  private def byId(id: TransactionId) = quote { transactions.filter(_.id == lift(id)) }
  private def byAccId(aid: AccountId) = quote(
    transactions.filter(t => t.destination.contains(lift(aid)) || t.source.contains(lift(aid)))
  )
  private def byAccIdInbound(aid: AccountId) = quote {
    transactions.filter(t => t.destination.contains(lift(aid)))
  }
  private def byAccIdOutbound(aid: AccountId) = quote {
    transactions.filter(t => t.source.contains(lift(aid)))
  }

}
