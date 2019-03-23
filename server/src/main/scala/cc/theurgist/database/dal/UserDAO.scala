package cc.theurgist.database.dal

import cc.theurgist.database.Db.InmemContext
import cc.theurgist.model.security.User
import com.typesafe.scalalogging.StrictLogging

class UserDAO(context: InmemContext) extends BaseCRUD[User](context) with StrictLogging {

  import ctx._
  private val users = quote(querySchema[User]("users"))

  def insert(c: User): Long =
    ctx.run { quote(users.insert(lift(c))) }

  def insert(cs: Seq[User]): List[Long] = ctx.run {
    quote {
      liftQuery(cs).foreach(c => users.insert(c))
    }
  }

  def delete(login: String): Long = ctx.run {
    byLogin(login).delete
  }

  def find(login: String): Option[User] = {
    extractUnique(ctx.run(quote { byLogin(login) }), s"login '$login'")
  }

  private def byLogin(login: String) = quote {
    users.filter(_.login == lift(login))
  }

}
