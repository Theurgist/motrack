package cc.theurgist.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

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

  implicit val encoder: Encoder[CurrencyId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[CurrencyId] = deriveUnwrappedDecoder
}