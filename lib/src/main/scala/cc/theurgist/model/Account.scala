package cc.theurgist.model

import java.time.LocalDateTime

import cc.theurgist.model.security.UserId
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

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

class AccountId(val id: Long) extends AnyVal
object AccountId {
  val none: AccountId = new AccountId(0L)

  implicit val encoder: Encoder[AccountId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[AccountId] = deriveUnwrappedDecoder
}
