package cc.theurgist.quill

import io.getquill.context.jdbc.JdbcContext

import scala.language.experimental.macros

trait MacroQueries {
  this: JdbcContext[_, _] =>
  def insertOrUpdate[T](entity: T, filter: T => Boolean): Unit = macro InsertOrUpdateMacro.insertOrUpdate[T]
}
