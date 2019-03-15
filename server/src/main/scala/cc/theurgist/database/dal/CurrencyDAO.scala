package cc.theurgist.database.dal

import cc.theurgist.model.Currency
import com.typesafe.scalalogging.StrictLogging
import io.getquill.{H2JdbcContext, SnakeCase}

class CurrencyDAO(ctx: H2JdbcContext[SnakeCase]) extends StrictLogging {
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
    ctx.run(quote {
      byCode(code)
    }) match {
      case value :: Nil => Some(value)
      case Nil          => None
      case _            => throw new Error(s"There are more than 1 values for code '$code'")
    }
  }

//    private def byName = quote {
//      (name: String) => by((c: Currency) => c.name == name)
//    }
//
//    def findByName(name: String) = {
//      ctx.run(byName,name)
//    }


  private def byCode(code: String) = quote {
    currencies.filter(_.code == lift(code))
  }

  //implicit val filterEncoder = MappedEncoding[Currency => Boolean, Boolean](f => f(_))

  private def findByName(name: String) = {

    // Dynamic
    ctx.run(quote { bySomething(
      (c: Currency) => c.name == lift(name))
    })

    // Dynamic
    val checker: ctx.Quoted[Currency => Boolean] = quote {
      c: Currency => c.name == lift(name)
    }
    ctx.run(quote { bySomething(checker) })

    // Dynamic
    val cf = quote { currencies.filter(c => checker(c)) }
    ctx.run(quote { cf })

    // How to do static??

  }

  // https://github.com/getquill/quill/issues/297
  private def bySomething(f: ctx.Quoted[Currency => Boolean]) = quote {
    currencies.filter(c => f(c))
  }

}
