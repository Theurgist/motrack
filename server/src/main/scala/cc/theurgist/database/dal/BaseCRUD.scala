package cc.theurgist.database.dal

import com.typesafe.scalalogging.StrictLogging
import io.getquill.{H2JdbcContext, SnakeCase}

import scala.language.experimental.macros

/**
  * NON-WORKING EXPERIMENT
  * Needs investigation. Because of strong compile-time types inferring engine is struggling with
  * generic type for quotes
  *
  *
  * Some Hints for this boilerplate-parade reduction (it needs macro...)
  *   https://stackoverflow.com/questions/44784310/how-to-write-generic-function-with-scala-quill-io-library/44797199#44797199
  *   https://github.com/getquill/quill-example/
  *
  */
abstract class BaseCRUD[T](protected val ctx: H2JdbcContext[SnakeCase]) extends StrictLogging {

  /**
    * Get exactly one optional value or throw an exception
    *
    * @param c collection from extract value to
    * @param err lazy error message supplement for bad situations logging
    * @return
    */
  protected def extractUnique(c: Iterable[T], err: => String): Option[T] = {
    c match {
      case value :: Nil => Some(value)
      case Nil          => None
      case _ =>
        val msg = s"There are more than 1 values: '$err'"
        logger.error(msg)
        throw new Error(msg)
    }
  }
  //implicit val encT: Encoder[T]
  //protected val table: ctx.Quoted[ctx.EntityQuery[T]] //= quote(querySchema[T](""))

//  protected def qInsert(c: T) = quote {
//    table.insert(lift(c))
//  }
//
//  protected def qInsert(cs: Iterable[T]) = quote {
//    liftQuery(cs).foreach(c => table.insert(c))
//  }

}
