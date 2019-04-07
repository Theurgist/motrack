package cc.theurgist.motrack.lib.model.account

import io.circe.Encoder
import io.circe.generic.extras.semiauto.deriveEnumerationEncoder

sealed trait AccountType {
  val code: Byte
  val name: Symbol

  override def toString: String = name.name
}
object AccountType {

  def apply(name: String): AccountType = name match {
    case BankAccount.name.name => BankAccount
    case CreditAccount.name.name => CreditAccount
    case PhysicalMoney.name.name => PhysicalMoney
    case Crypto.name.name => Crypto
    case Securities.name.name => Securities
    case _ => throw new IllegalArgumentException("Bad AccountType name")
  }

  def apply(code: Byte): AccountType = code match {
    case 1 => BankAccount
    case 2 => CreditAccount
    case 3 => PhysicalMoney
    case 4 => Crypto
    case 5 => Securities
    case _ => throw new IllegalArgumentException(s"Badd account type code: $code")
  }

  val allTypes: Array[AccountType] = Array(BankAccount, CreditAccount, PhysicalMoney, Crypto, Securities)


  implicit val encoder: Encoder[AccountType] = deriveEnumerationEncoder[AccountType]
}

case object BankAccount   extends AccountType { val code = 1; val name = 'BankAccount   }
case object CreditAccount extends AccountType { val code = 2; val name = 'CreditAccount }
case object PhysicalMoney extends AccountType { val code = 3; val name = 'PhysicalMoney }
case object Crypto        extends AccountType { val code = 4; val name = 'Crypto        }
case object Securities    extends AccountType { val code = 5; val name = 'Securities    }
