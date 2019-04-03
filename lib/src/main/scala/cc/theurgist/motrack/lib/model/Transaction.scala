package cc.theurgist.motrack.lib.model

import java.time.LocalDateTime

import cc.theurgist.motrack.lib.model.security.UserId
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

case class Transaction
(
    id: TransactionId,
    actor: UserId,
    at: LocalDateTime,
    source: Option[AccountId],
    destination: Option[AccountId],
    conversionRate: Double,
    amount: Double
) extends WithId[TransactionId]

class TransactionId(val id: Long) extends AnyVal
object TransactionId {
  val none: TransactionId = new TransactionId(0L)

  implicit val encoder: Encoder[TransactionId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[TransactionId] = deriveUnwrappedDecoder
}
