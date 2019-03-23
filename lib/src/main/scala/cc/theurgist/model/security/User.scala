package cc.theurgist.model.security

import cc.theurgist.model.WithId
import cc.theurgist.security.PasswordValidation.{EncryptedPassword, PasswordSalt}

/**
  * End user
  */
case class User(
    id: UserId,
    login: String,
    name: String,
    password: EncryptedPassword,
    salt: PasswordSalt
) extends WithId[UserId]

case class UserId(id: Int) extends AnyVal
object UserId {
  val none: UserId = UserId(0)
}