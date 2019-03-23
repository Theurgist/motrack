package cc.theurgist.model

import java.time.LocalDateTime

import cc.theurgist.model.security.UserId

case class Transaction
(
    id: TransactionId,
    actor: UserId,
    at: LocalDateTime,
    source: Option[Int],
    destination: Option[Int],
    conversionRate: Double
) extends WithId[TransactionId]

case class TransactionId(id: Long) extends AnyVal
object TransactionId {
  val none: TransactionId = TransactionId(0L)
}
