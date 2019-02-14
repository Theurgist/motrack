package cc.theurgist

import cc.theurgist.dao.CurrencyDao
import cc.theurgist.model.Currency
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import slick.jdbc.H2Profile.api._

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

//trait H2db extends SuiteMixin { this: Suite =>
//  val db = Database.forConfig("h2mem1")
//
//  abstract override def withFixture(test: NoArgTest) = {
//    // Shared setup (run at beginning of each test)
//    try {
//      super.withFixture(test)
//    }
//    finally {
//      // Shared cleanup (run at end of each test)
//      db.close()
//    }
//  }
//}

trait Buffer extends SuiteMixin with TestSuite {

  val buffer = new ListBuffer[String]

  abstract override def withFixture(test: NoArgTest): Outcome = {
    try super.withFixture(test) // To be stackable, must call super.withFixture
    finally buffer.clear()
  }
}

import slick.jdbc.{H2Profile, SQLiteProfile}
class SlickH2Test extends WordSpec {
  import slick.jdbc.H2Profile.api._

  "app " should {
    "populate db" in {
      val db = Database.forConfig("h2mem1")

      try {
        val cd = new CurrencyDao(H2Profile)

        val setup = DBIO.seq(
          (cd.tab.schema).create,
          cd.tab ++= Seq(
            //Currency("rur", "Russian Ruble", "\u20BD", Option("Russian Federation"), isCrypto = false),
            Currency("usd", "United States Dollar", "$", Option("United States of America"), isCrypto = false),
            Currency("eur", "Euro", "€", Option("Europe"), isCrypto = false),
          ),
        )
        val ddlComplete = db.run(setup.transactionally)

//        (for {
//          _ <- db.run(setup)
//          _ = println("Coffees:")
//          _ <- db.run(cd.tab.result).map(_.foreach {
//            case c: Currency =>
//              println("cc:"+c.unicode)
//          })
//        } ()).onComplete { _ => db.close }

        Await.result(ddlComplete, Duration.Inf)

//        val f = ddlComplete.flatMap { _ =>
//        }

        ddlComplete.onComplete(c => {
          println("Currencies:")

          db.run(cd.tab.result).map(_.foreach {
            case c: Currency =>
              println("cc:"+c.unicode)
          })
        })
        ddlComplete.failed.foreach(f => println(s"F: $f"))

        //println("Currencies:")
        //db.run(cd.currencies.result).map(_.foreach {
        //  case c: Currency =>
        //    println(c.unicode)
        //})

        // val resultFuture: Future[_] = { ... }
        //Await.result(resultFuture, Duration.Inf)
        //lines.foreach(Predef.println _)
        println
      } finally {
        db.close
      }

    }
  }
}
