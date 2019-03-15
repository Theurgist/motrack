package cc.theurgist.database.dal

import cc.theurgist.model.security.User
import io.getquill.{H2JdbcContext, SnakeCase}

class UserDAO(ctx: H2JdbcContext[SnakeCase]) {

  import ctx._
  private val users = quote(querySchema[User]("users"))

}
