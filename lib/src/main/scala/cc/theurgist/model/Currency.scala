package cc.theurgist.model

/**
  * Any countable money entity should be represented as this
  */
case class Currency(
    id: CurrencyId,
    code: String,
    name: String,
    unicode: String,
    country: Option[String],
    isCrypto: Boolean
) extends Entity with WithId[CurrencyId]


class CurrencyId(val id: Int) extends AnyVal
object CurrencyId {
  val none: CurrencyId = new CurrencyId(0)
}