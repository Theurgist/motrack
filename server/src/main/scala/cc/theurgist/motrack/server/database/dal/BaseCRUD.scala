package cc.theurgist.motrack.server.database.dal

import cc.theurgist.motrack.server.database.Db.InmemContext
import com.typesafe.scalalogging.StrictLogging

import scala.language.experimental.macros

/**
  * Needs investigation. Because of quite rigid compile-time types inferring engine is struggling with
  * generic type for quotes. Even if code does compile - any attempt to extract generified methods
  * breaks static query compilation, forcing fallback to dynamic query because of mandatory
  * types annotation, which, on the other hand, should be avoided while using Quill.
  *
  *
  * Some Hints for this boilerplate-parade reduction (it needs macro magic)
  *   https://stackoverflow.com/questions/44784310/how-to-write-generic-function-with-scala-quill-io-library/44797199#44797199
  *   https://github.com/getquill/quill-example/
  *
  */
abstract class BaseCRUD[T](protected val ctx: InmemContext) extends StrictLogging {

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

}
