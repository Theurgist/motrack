package cc.theurgist.motrack.lib.model.security.session

import java.time.LocalDateTime

import cc.theurgist.motrack.lib.model.security.user.UserId

case class Session
(
    id: SessionId,
    userId: UserId,
    createdAt: LocalDateTime,
    lastActivityAt: LocalDateTime
)
