package cc.theurgist.database.dal

import cc.theurgist.database.Db.InmemContext
import cc.theurgist.model.Currency
import cc.theurgist.model.security.User
import com.typesafe.scalalogging.StrictLogging
import io.getquill.{H2JdbcContext, SnakeCase}

class UserDAO(context: InmemContext) extends BaseCRUD[User](context) with StrictLogging {

  import ctx._
  private val users = quote(querySchema[User]("users"))

}
