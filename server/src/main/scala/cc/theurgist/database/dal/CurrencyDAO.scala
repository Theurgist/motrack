package cc.theurgist.database.dal

import cc.theurgist.model.Currency
import com.typesafe.scalalogging.StrictLogging
import io.getquill.{H2JdbcContext, SnakeCase}

class CurrencyDAO(context: H2JdbcContext[SnakeCase]) extends BaseCRUD[Currency](context) with StrictLogging {
  import ctx._
  private val currencies = quote(querySchema[Currency]("currencies"))

  def insert(c: Currency): Long = ctx.run { quote(currencies.insert(lift(c))) }

  def insert(cs: Seq[Currency]): List[Long] = ctx.run {
    quote {
      liftQuery(cs).foreach(c => currencies.insert(c))
    }
  }

  def delete(code: String): Long = {
    ctx.run(byCode(code).delete)
  }

  def findByCode(code: String): Option[Currency] = {
    extractUnique(ctx.run(quote { byCode(code) }), s"code '$code'")
  }
  
  def findByName(name: String): List[Currency] = {
    // Compiles as static
    val cf = quote { byName(name) }
    ctx.run(quote { cf })
  }

  private def byCode(code: String) = quote { currencies.filter(_.code == lift(code)) }
  private def byName(name: String)                                 = quote { currencies.filter(_.name == lift(name)) }
  private def byNamePartial(a: ctx.Quoted[Currency])(name: String) = quote { a.name == lift(name) }
}
