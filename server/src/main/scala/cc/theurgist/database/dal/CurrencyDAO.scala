package cc.theurgist.database.dal

import cc.theurgist.model.Currency
import io.getquill.{H2JdbcContext, SnakeCase}

class CurrencyDAO(ctx: H2JdbcContext[SnakeCase]) {
  import ctx._

  def insert(c: Currency): Long = {
    def q = quote {
      query[Currency].insert(lift(c))
    }
    ctx.run(q)
  }
  def insert(cs: Seq[Currency]): List[Long] =
    ctx.run(quote {
      liftQuery(cs).foreach(c => query[Currency].insert(c))
    })

  def findByCode(code: String): List[Currency] = {
    def q = quote {
      query[Currency].filter(_.code == lift(code))
    }
    ctx.run(q)
  }
}
