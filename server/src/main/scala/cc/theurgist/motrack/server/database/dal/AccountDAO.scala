package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.lib.model.account.{Account, AccountId}
import com.typesafe.scalalogging.StrictLogging
import cc.theurgist.motrack.server.database.QuillEncoders.{decodeAccountType, encodeAccountType}

class AccountDAO(context: InmemContext) extends BaseCRUD[Account](context) with StrictLogging {

  import ctx._
  private val accounts = quote(querySchema[Account]("accounts"))

  def insert(o: Account): AccountId =
    ctx.run { quote(accounts.insert(lift(o))).returning(_.id) }

  def insert(ox: Seq[Account]): List[AccountId] = ctx.run {
    quote(liftQuery(ox).foreach(o => accounts.insert(o).returning(_.id)))
  }

  def delete(id: AccountId): Long = ctx.run { byId(id).delete }

  def find(id: AccountId): Option[Account] = {
    extractUnique(ctx.run(quote { byId(id) }), s"id '$id'")
  }

  private def byId(id: AccountId) = quote { accounts.filter(_.id == lift(id)) }

}
