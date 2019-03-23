package cc.theurgist.database.dal

import cc.theurgist.database.Db.InmemContext
import cc.theurgist.model.{Currency, CurrencyId}
import com.typesafe.scalalogging.StrictLogging

class CurrencyDAO(context: InmemContext) extends BaseCRUD[Currency](context) with StrictLogging {
  import ctx._
  private val currencies = quote(querySchema[Currency]("currencies"))

  def insert(o: Currency): CurrencyId =
    ctx.run { quote(currencies.insert(lift(o))).returning(_.id) }

  def insert(ox: Seq[Currency]): List[CurrencyId] = ctx.run {
    quote(liftQuery(ox).foreach(o => currencies.insert(o).returning(_.id)))
  }

  def delete(code: String): Long = { ctx.run(byCode(code).delete) }

//  def find(filter: Any) = {
//    ctx.run(quote { filter })
//  }
//
//  def findGenerifiedByName(name: String) = {
//    val cf = quote { byName(name) }
//    find(cf)
//  }

  def find(id: CurrencyId): Option[Currency] = { extractUnique(ctx.run(quote { byId(id) }), s"id '$id'") }
  def findByCode(code: String): Option[Currency] = {
    extractUnique(ctx.run(quote { byCode(code) }), s"code '$code'")
  }

  def findByName(name: String): List[Currency] = {
    // Compiles as static
    val cf = quote { byName(name) }
    ctx.run(quote { cf })
  }

  private def byId(id: CurrencyId) = quote { currencies.filter(_.id == lift(id)) }
  private def byCode(code: String) = quote { currencies.filter((o: Currency) => o.code == lift(code)) }
  private def byName(name: String) = quote { currencies.filter(_.name == lift(name)) }
  private def byNamePartial(a: ctx.Quoted[Currency])(name: String) = quote {
    a.name == lift(name)
  }

}
