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


case class CurrencyId(id: Int) extends AnyVal
object CurrencyId {
  val none: CurrencyId = CurrencyId(0)
}