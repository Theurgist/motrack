package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db.InmemContext
import cc.theurgist.motrack.lib.model.Currency
import com.typesafe.scalalogging.StrictLogging

class CurrencyDAOExperiments(context: InmemContext) extends BaseCRUD(context) with StrictLogging {
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

  private def byCode(code: String) = quote {
    currencies.filter(_.code == lift(code))
  }

//    private def byName = quote {
//      (name: String) => by((c: Currency) => c.name == name)
//    }
//
//    def findByName(name: String) = {
//      ctx.run(byName,name)
//    }

  def findByCode(code: String): Option[Currency] = {
    ctx.run(quote {
      byCode(code)
    }) match {
      case value :: Nil => Some(value)
      case Nil          => None
      case _            => throw new Error(s"There are more than 1 values for code '$code'")
    }
  }

  //implicit val filterEncoder = MappedEncoding[Currency => Boolean, Boolean](f => f(_))

  private def findByName(name: String) = {

    // Dynamic
    ctx.run(quote { bySomething((c: Currency) => c.name == lift(name)) })

    // Dynamic
    def checker = quote { c: Currency =>
      c.name == lift(name)
    }
    ctx.run(quote { bySomething(checker) })

    // NOW STATIC!!
    def cf = quote { currencies.filter(c => checker(c)) }
    ctx.run(quote { cf })

    case class Comprtor(c: Currency) {
      def withName(n: String) = quote { c.name == lift(n) }
      //def withCode(c: Currency, code: String) = quote {c.code == lift(code) }
    }
    def comparer(c: Currency) = Comprtor(c).withName(name)
    /*
    def cf2 = quote { currencies.filter(c => comparer(c)) }
    ctx.run(quote { cf2 })

    // Reference static query
    ctx.run(quote { currencies.filter((c: Currency) => c.name == lift(name)) })

    // How to do static with little generification??
   */
  }

  // https://github.com/getquill/quill/issues/297
  private def bySomething(f: ctx.Quoted[Currency => Boolean]) = quote {
    currencies.filter(c => f(c))
  }

  def insertOrUpdateThroughMacro(c: Currency) = {
    //ctx.run(insertOrUpdate(c, (t: Currency) => t.code == lift(c.code)))
  }

  //https://github.com/getquill/quill/blob/master/CHANGELOG.md
  def maybeItWillHelp() = {
    // Anonymous classes aren't supported for function declaration anymore ...
    // old
    //    val q = quote { new { def apply[T](q: Query[T]) = ... } }
    //  new
    //    def q[T] = quote { (q: Query[T] => ... }
  }

}
