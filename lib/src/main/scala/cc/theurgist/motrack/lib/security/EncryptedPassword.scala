package cc.theurgist.motrack.lib.security

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

case class EncryptedPassword(encrypted: String) extends AnyVal

object EncryptedPassword {
  implicit val encoder: Encoder[EncryptedPassword] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[EncryptedPassword] = deriveUnwrappedDecoder
}