package cc.theurgist.config

sealed trait Db {

}

object Db {
  case object Inmem extends Db
  case object Persistent extends Db
}