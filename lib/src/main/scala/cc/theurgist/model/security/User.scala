package cc.theurgist.model.security

import cc.theurgist.model.WithId

/**
  * End user
  */
case class User(
    id: UserId,
    login: String,
    name: String,
) extends WithId[UserId]

case class UserId(id: Int) extends AnyVal
