package cc.theurgist.motrack.lib.model.currency

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

class CurrencyId(val id: Int) extends AnyVal
object CurrencyId {
  val none: CurrencyId = new CurrencyId(0)

  implicit val encoder: Encoder[CurrencyId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[CurrencyId] = deriveUnwrappedDecoder
}
