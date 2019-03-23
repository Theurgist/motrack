package cc.theurgist.security

import java.security.SecureRandom

/**
  * Security über alles
  * TODO make a real secure version
  */
object PasswordValidation {

  case class EncryptedPassword(encrypted: String) extends AnyVal
  case class PasswordSalt(salt: String)           extends AnyVal
  case class PasswordData(pwd: EncryptedPassword, salt: PasswordSalt)

  private def saltHolder: PasswordSalt = PasswordSalt("-SALT-")

  // TODO secure
  def encrypt(rawPassword: String): PasswordData = {
    PasswordData(EncryptedPassword(rawPassword + saltHolder), saltHolder)
  }

  // TODO secure
  def validate(rawPassword: String, reference: PasswordData): Boolean = {
    rawPassword + saltHolder == reference.pwd.encrypted
  }

}
