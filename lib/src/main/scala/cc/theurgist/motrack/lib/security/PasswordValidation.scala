package cc.theurgist.motrack.lib.security

/**
  * Security über alles
  * TODO make a real secure version
  */
object PasswordValidation {

  case class PasswordData(pwd: EncryptedPassword, salt: PasswordSalt)

  private def saltHolder: PasswordSalt = PasswordSalt("")

  // TODO secure
  def encrypt(rawPassword: String): PasswordData = {
    PasswordData(EncryptedPassword(rawPassword), saltHolder)
  }

  // TODO secure
  def validate(rawPassword: String, reference: PasswordData): Boolean = {
    rawPassword == reference.pwd.encrypted
  }
}
