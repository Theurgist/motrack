package cc.theurgist.dao

import cc.theurgist.model.Currency
import slick.jdbc.JdbcProfile
import scala.language.higherKinds
//import slick.jdbc.H2Profile.api._


class CurrencyDao(val profile: JdbcProfile) {
  import profile.api._

  class CurrencyTable(tag: Tag) extends Table[Currency](tag, "CURRENCY") {
    def code = column[String]("CODE", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME", O.Unique)
    def unicode = column[String]("UNICODE", O.Unique)
    def country = column[Option[String]]("COUNTRY")
    def isCrypto = column[Boolean]("IS_CRYPTO")
    override def * = (code, name, unicode, country, isCrypto).mapTo[Currency]
  }

  val currencies = TableQuery[CurrencyTable]

  /** Create the database schema */
  def create: DBIO[Unit] =
    currencies.schema.create

  /** Insert a key/value pair */
  def insert(v: Currency): DBIO[Int] =
    currencies += v

  /** Get the value for the given key */
  def get(code: String): DBIO[Option[Currency]] =
    (for (p <- currencies if p.code === code) yield p.value).result.headOption

  /** Get the first element for a Query from this DAO */
  def getFirst[M, U, C[_]](q: Query[M, U, C]): DBIO[U] =
    q.result.head

//  def getAll =
//    currencies.map(c => c).result
}
