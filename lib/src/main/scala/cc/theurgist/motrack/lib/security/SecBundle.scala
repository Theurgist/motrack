package cc.theurgist.motrack.lib.security

import cc.theurgist.motrack.lib.model.security.session.SessionId

case class SecBundle(
    sid: SessionId,
    authToken: String,
    refreshToken: String,
    xsrf: String,
)
