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


//object MyEnum extends Enumeration {
//  type MyEnum = Value
//  val A = Value("a")
//  val B = Value("b")
//  val C = Value("c")
//}
