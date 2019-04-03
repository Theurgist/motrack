package cc.theurgist.motrack.lib.security

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

case class PasswordSalt(salt: String) extends AnyVal

object PasswordSalt {
  implicit val encoder: Encoder[PasswordSalt] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[PasswordSalt] = deriveUnwrappedDecoder
}