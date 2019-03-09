package cc.theurgist.slickdao

import cc.theurgist.model.{Account, AccountType}
import cc.theurgist.model.AccountType.AccountType
import slick.ast.BaseTypedType
import slick.jdbc.{JdbcProfile, JdbcType}

import scala.language.higherKinds

@Deprecated
class AccountDao(val profile: JdbcProfile) {
  val currencyDao = new CurrencyDao(profile)
  //import profile.api._
  import slick.jdbc.H2Profile.api._

  implicit val accountTypeMapper: JdbcType[AccountType] with BaseTypedType[AccountType] = MappedColumnType.base[AccountType, String](
    e => e.toString,
    s => AccountType.withName(s)
  )

  class AccountTab(tag: Tag) extends Table[Account](tag, "CURRENCY") {
    def id = column[String]("ID", O.PrimaryKey, O.AutoInc)
    def ownerId = column[String]("OWNER_ID")
    def name = column[String]("NAME")
    def accType = column[AccountType]("ACC_TYPE")
    def createdAt = column[Long]("CREATED_AT")
    def currencyCode = column[String]("CURRENCY_CODE")

    override def * = (ownerId, name, accType, createdAt, currencyCode, id.?).mapTo[Account]

    def currency = foreignKey("FK_CURR", currencyCode, currencyDao.tab)(_.code)
  }

  val tab = TableQuery[AccountTab]

  /** Create the database schema */
  def create: DBIO[Unit] =
    tab.schema.create

  /** Insert a key/value pair */
  def insert(v: Account): DBIO[Int] =
    tab += v

  /** Get the value for the given key */
  def get(id: String): DBIO[Option[Account]] =
    (for (p <- tab if p.id === id) yield p.value).result.headOption

  /** Get the first element for a Query from this DAO */
  def getFirst[M, U, C[_]](q: Query[M, U, C]): DBIO[U] =
    q.result.head

//  def getAll =
//    currencies.map(c => c).result
}
