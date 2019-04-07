package cc.theurgist.motrack.lib.model.transaction

import java.time.LocalDateTime

import cc.theurgist.motrack.lib.model.WithId
import cc.theurgist.motrack.lib.model.account.AccountId
import cc.theurgist.motrack.lib.model.security.user.UserId
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}
import io.circe.{Decoder, Encoder}

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

