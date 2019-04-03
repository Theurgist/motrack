package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.lib.model.security.{User, UserId}
import com.typesafe.scalalogging.StrictLogging

class UserDAO(context: InmemContext) extends BaseCRUD[User](context) with StrictLogging {

  import ctx._
  private val users = quote(querySchema[User]("users"))

  def insert(o: User): UserId =
    ctx.run { quote(users.insert(lift(o)).returning(_.id)) }

  def insert(ox: Seq[User]): List[UserId] = ctx.run {
    quote(liftQuery(ox).foreach(o => users.insert(o).returning(_.id)))
  }

  def delete(id: UserId): Long    = ctx.run { byId(id).delete }
  def delete(login: String): Long = ctx.run { byLogin(login).delete }

  def find(id: UserId): Option[User] = { extractUnique(ctx.run(quote { byId(id) }), s"id '$id'") }
  def find(login: String): Option[User] = {
    extractUnique(ctx.run(quote { byLogin(login) }), s"login '$login'")
  }

  private def byId(id: UserId)       = quote { users.filter(_.id == lift(id)) }
  private def byLogin(login: String) = quote { users.filter(_.login == lift(login)) }

}
