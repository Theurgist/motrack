package cc.theurgist.motrack.lib.model.security.session

import io.circe.{Decoder, Encoder}
import io.circe.generic.extras.semiauto.{deriveUnwrappedDecoder, deriveUnwrappedEncoder}

class SessionId(val id: Int) extends AnyVal
object SessionId {
  val none: SessionId = new SessionId(0)

  implicit val encoder: Encoder[SessionId] = deriveUnwrappedEncoder
  implicit val decoder: Decoder[SessionId] = deriveUnwrappedDecoder
}