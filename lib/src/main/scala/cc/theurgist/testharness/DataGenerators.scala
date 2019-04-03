package cc.theurgist.testharness

import java.time.{LocalDateTime, ZoneOffset}

import cc.theurgist.model.security.{User, UserId}
import cc.theurgist.model._
import cc.theurgist.security.PasswordValidation

import scala.language.postfixOps
import scala.util.Random

/**
  * Object for generating some raw data without saving it in any data store
  */
object DataGenerators {

  def genPassword: PasswordValidation.PasswordData =
    PasswordValidation.encrypt(Random.nextString(50))
  def genLocalDateTime: LocalDateTime =
    LocalDateTime.ofEpochSecond(Random.nextLong() % 1553414782L, Random.nextInt(1000), ZoneOffset.UTC)
  def genAccountType: AccountType =
    AccountType.allTypes(Random.nextInt(AccountType.allTypes.length))

  def genCurrencies(count: Int): List[Currency] = {
    for {
      _ <- 0 until count toList
    } yield
      Currency(
        CurrencyId.none,
        Random.nextString(20),
        Random.nextString(20),
        Random.nextString(4),
        None,
        Random.nextBoolean()
      )
  }

  def genUsers(count: Int): List[User] = {
    for {
      _ <- 0 until count toList
    } yield {
      val pwd = genPassword
      User(
        UserId.none,
        Random.nextString(20),
        Random.nextString(20),
        pwd.pwd,
        pwd.salt
      )
    }
  }

  def genAccounts(countsForUsers: List[(Int, User)], currency: Currency): List[Account] = {
    for {
      (n, u) <- countsForUsers
      _      <- 1 to n
    } yield
      Account(
        AccountId.none,
        u.id,
        currency.id,
        Random.nextString(5),
        genAccountType,
        genLocalDateTime
      )
  }

  def genTransactions(n: Int, from: Account, to: Account): List[Transaction] = {
    for {
      _ <- 1 to n toList
    } yield
      Transaction(
        TransactionId.none,
        from.ownerId,
        genLocalDateTime,
        Some(from.id),
        Some(to.id),
        0.5 + Random.nextDouble(),
        Random.nextInt()
      )
  }
}
