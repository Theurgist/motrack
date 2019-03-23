package cc.theurgist.model

import java.time.LocalDateTime

import cc.theurgist.model.security.UserId

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

case class TransactionId(id: Long) extends AnyVal
object TransactionId {
  val none: TransactionId = TransactionId(0L)
}
