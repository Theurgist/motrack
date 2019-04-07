package cc.theurgist.motrack.lib.model.transaction

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

class TransactionId(val id: Long) extends AnyVal
object TransactionId {
  val none: TransactionId = new TransactionId(0L)

  implicit val encoder: Encoder[TransactionId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[TransactionId] = deriveUnwrappedDecoder
}