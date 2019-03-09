package cc.theurgist.slickdao

import cc.theurgist.model.Currency
import slick.jdbc.JdbcProfile
import scala.language.higherKinds
//import slick.jdbc.H2Profile.api._

@Deprecated
class CurrencyDao(val profile: JdbcProfile) {
  //import profile.api._
  import slick.jdbc.H2Profile.api._

  class CurrencyTab(tag: Tag) extends Table[Currency](tag, "CURRENCY") {
    def code = column[String]("CODE", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME", O.Unique)
    def unicode = column[String]("UNICODE", O.Unique)
    def country = column[Option[String]]("COUNTRY")
    def isCrypto = column[Boolean]("IS_CRYPTO")
    override def * = (code, name, unicode, country, isCrypto).mapTo[Currency]
  }

  val tab = TableQuery[CurrencyTab]

  /** Create the database schema */
  def create: DBIO[Unit] =
    tab.schema.create

  /** Insert a key/value pair */
  def insert(v: Currency): DBIO[Int] =
    tab += v

  /** Get the value for the given key */
  def get(code: String): DBIO[Option[Currency]] =
    (for (p <- tab if p.code === code) yield p.value).result.headOption

  /** Get the first element for a Query from this DAO */
  def getFirst[M, U, C[_]](q: Query[M, U, C]): DBIO[U] =
    q.result.head

//  def getAll =
//    currencies.map(c => c).result
}
