package cc.theurgist.motrack.lib.model.security.user

import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}
import io.circe.{Decoder, Encoder}

class UserId(val id: Int) extends AnyVal
object UserId {
  val none: UserId = new UserId(0)

  implicit val encoder: Encoder[UserId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[UserId] = deriveUnwrappedDecoder
}