package cc.theurgist.security

import java.security.SecureRandom

/**
  * Security über alles
  */
object PasswordGenerator {

  case class PasswordData (encrypted: String, salt: String)

  def encrypt(rawPassword: String): PasswordData = {
    ???
  }

  def validate(rawPassword: String, reference: PasswordData): Boolean = {
    ???
  }


}
