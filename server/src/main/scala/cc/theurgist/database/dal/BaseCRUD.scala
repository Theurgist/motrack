package cc.theurgist.database.dal

import io.getquill.{H2JdbcContext, SnakeCase}

import scala.collection.Iterable
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
abstract class BaseCRUD[T](protected val ctx: H2JdbcContext[SnakeCase], tableName: String) {
  import ctx._
  implicit val encT: Encoder[T]
  protected val table: ctx.Quoted[ctx.EntityQuery[T]] //= quote(querySchema[T](""))

  protected def qInsert(c: T) = quote {
    table.insert(lift(c))
  }

  protected def qInsert(cs: Iterable[T]) = quote {
    liftQuery(cs).foreach(c => table.insert(c))
  }

}
