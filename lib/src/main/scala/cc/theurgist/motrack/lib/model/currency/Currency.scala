package cc.theurgist.motrack.lib.model.currency

import cc.theurgist.motrack.lib.model.{Entity, WithId}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}
import io.circe.{Decoder, Encoder}

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
