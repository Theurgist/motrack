package cc.theurgist.motrack.lib.model.security.session

import java.time.LocalDateTime

import cc.theurgist.motrack.lib.model.security.user.UserId

import scala.language.implicitConversions

case class Session
(
    id: SessionId,
    userId: UserId,
    createdAt: LocalDateTime,
    lastActivityAt: LocalDateTime
)

object Session {
  implicit def toSessionId(s: Session): SessionId = s.id
}