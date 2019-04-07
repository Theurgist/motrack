package cc.theurgist.motrack.lib.model.account

import java.time.LocalDateTime

import cc.theurgist.motrack.lib.model.WithId
import cc.theurgist.motrack.lib.model.currency.CurrencyId
import cc.theurgist.motrack.lib.model.security.user.UserId
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}
import io.circe.{Decoder, Encoder}

/**
  * Money deposit entry
  */
case class Account(
    id: AccountId,
    ownerId: UserId,
    currencyId: CurrencyId,
    name: String,
    accType: AccountType,
    createdAt: LocalDateTime,
) extends WithId[AccountId]

