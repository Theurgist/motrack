package cc.theurgist.database.dal

import cc.theurgist.database.Db.InmemContext
import cc.theurgist.model.{Transaction, TransactionId}
import com.typesafe.scalalogging.StrictLogging

class TransactionDAO(context: InmemContext) extends BaseCRUD[Transaction](context) with StrictLogging {

  import ctx._
  private val transactions = quote(querySchema[Transaction]("transactions"))

  def insert(o: Transaction): TransactionId =
    ctx.run { quote(transactions.insert(lift(o))).returning(_.id) }

  def insert(ox: Seq[Transaction]): List[TransactionId] = ctx.run {
    quote(liftQuery(ox).foreach(o => transactions.insert(o).returning(_.id)))
  }

  def delete(id: TransactionId): Long = ctx.run { byId(id).delete }

  def find(id: TransactionId): Option[Transaction] = {
    extractUnique(ctx.run(quote { byId(id) }), s"id '$id'")
  }

  private def byId(id: TransactionId) = quote { transactions.filter(_.id == lift(id)) }

}
