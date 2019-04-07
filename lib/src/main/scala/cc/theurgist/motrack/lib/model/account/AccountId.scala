package cc.theurgist.motrack.lib.model.account

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}


class AccountId(val id: Long) extends AnyVal {
  def apply(): Long = id
}
object AccountId {
  val none: AccountId = new AccountId(0L)

  implicit val encoder: Encoder[AccountId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[AccountId] = deriveUnwrappedDecoder
}