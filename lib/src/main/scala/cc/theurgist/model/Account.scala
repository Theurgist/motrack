package cc.theurgist.model

import java.time.LocalDateTime

import cc.theurgist.model.security.UserId

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

case class AccountId(id: Long) extends AnyVal
object AccountId {
  val none: AccountId = AccountId(0L)
}
