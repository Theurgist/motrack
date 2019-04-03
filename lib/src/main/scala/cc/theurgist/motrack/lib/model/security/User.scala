package cc.theurgist.motrack.lib.model.security

import cc.theurgist.motrack.lib.model.WithId
import cc.theurgist.motrack.lib.security.{EncryptedPassword, PasswordSalt}
import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

/**
  * End user
  */
case class User(
    id: UserId,
    login: String,
    name: String,
    password: EncryptedPassword,
    salt: PasswordSalt
) extends WithId[UserId]

class UserId(val id: Int) extends AnyVal
object UserId {
  val none: UserId = new UserId(0)

  implicit val encoder: Encoder[UserId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[UserId] = deriveUnwrappedDecoder
}