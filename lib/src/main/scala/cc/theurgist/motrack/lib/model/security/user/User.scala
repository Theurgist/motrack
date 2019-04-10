package cc.theurgist.motrack.lib.model.security.user

import cc.theurgist.motrack.lib.model.WithId
import cc.theurgist.motrack.lib.security.{EncryptedPassword, PasswordSalt}

/**
  * End user
  */
case class User(
    id: UserId,
    login: String,
    name: String,
    password: EncryptedPassword,
    salt: PasswordSalt
) extends WithId[UserId] {
  def safePart: SafeUser = SafeUser(id, login, name)
}

case class SafeUser
(
  id: UserId,
  login: String,
  name: String,
) extends WithId[UserId]