package cc.theurgist.model

sealed trait AccountType {
  val code: Byte
}
object AccountType {

  def apply(code: Byte): AccountType = code match {
    case 1 => BankAccount
    case 2 => CreditAccount
    case 3 => PhysicalMoney
    case 4 => Crypto
    case 5 => Securities
    case _ => throw new IllegalArgumentException(s"Badd account type code: $code")
  }
}

case object BankAccount   extends AccountType { val code = 1; override def toString: String = "BankAccount"   }
case object CreditAccount extends AccountType { val code = 2; override def toString: String = "CreditAccount" }
case object PhysicalMoney extends AccountType { val code = 3; override def toString: String = "PhysicalMoney" }
case object Crypto        extends AccountType { val code = 4; override def toString: String = "Crypto"        }
case object Securities    extends AccountType { val code = 5; override def toString: String = "Securities"    }
