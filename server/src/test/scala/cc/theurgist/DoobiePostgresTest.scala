package cc.theurgist

import org.scalatest.WordSpec

import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._

class DoobiePostgresTest extends WordSpec {
  import scala.concurrent.ExecutionContext


  "app " should {
    "follow doobie tutorial" in {
      // We need a ContextShift[IO] before we can construct a Transactor[IO]. The passed ExecutionContext
      // is where nonblocking operations will be executed.
      implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

      // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
      // on an unbounded pool of daemon threads. See the chapter on connection handling for more info.
      val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver", // driver classname
        "jdbc:postgresql:world", // connect URL (driver-specific)
        "postgres",             // user
        "empty"                // password
      )

      val program1 = 42.pure[ConnectionIO]
      val io = program1.transact(xa)
      println(s"Program1 result: ${io.unsafeRunSync}")

      val program2 = sql"select 42".query[Int].unique
      val io2 = program2.transact(xa)
      println(s"Program2 result: ${io2.unsafeRunSync}")

      // Monadic way
      val program3: ConnectionIO[(Int, Double)] =
        for {
          a <- sql"select 42".query[Int].unique
          b <- sql"select random()".query[Double].unique
        } yield (a, b)
      println(s"Program3 result: ${program3.transact(xa).unsafeRunSync}")

      // The astute among you will note that we don’t actually need a monad to do this; an applicative functor is all we need here.
      val program3a = {
        val a: ConnectionIO[Int] = sql"select 42".query[Int].unique
        val b: ConnectionIO[Double] = sql"select random()".query[Double].unique
        (a, b).tupled
      }
      println(s"Program3a result: ${program3a.transact(xa).unsafeRunSync}")

      val valuesList = program3a.replicateA(5)
      val result = valuesList.transact(xa)
      println(s"Replicated Program3a result: ${result.unsafeRunSync}")

    }
  }
}
