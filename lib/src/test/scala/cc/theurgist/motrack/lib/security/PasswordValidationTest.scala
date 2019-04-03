package cc.theurgist.motrack.lib.security

import org.scalatest.{Matchers, WordSpec}

import scala.util.Random

//TODO add property test
class PasswordValidationTest extends WordSpec with Matchers {
  "Passwords validator" should {
    "validate own generated passwords" in {
      val pwds = (1 to (10 + Random.nextInt(10)))
        .map(n => Random.nextString(20))
        .map(p => (p, PasswordValidation.encrypt(p)))
      pwds.foreach(p => PasswordValidation.validate(p._1, p._2) shouldBe true)
    }
  }
}
