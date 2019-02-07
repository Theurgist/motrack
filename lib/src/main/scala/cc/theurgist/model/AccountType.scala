package cc.theurgist.model

object AccountType extends Enumeration {
  type AccountType = Value
  val
    BankAccount,
    CreditAccount,
    PhysicalMoney,
    Crypto,
    Securities
    = Value
}
