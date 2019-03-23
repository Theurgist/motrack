package cc.theurgist.model.security

import java.time.LocalDateTime

/**
  * Entry about some user's actions, likke login, logout etc
  */
case class UserAction(
    at: LocalDateTime
)
