package cc.theurgist

import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

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

class SlickH2Test extends WordSpec {

  "app " should {
    "populate db" in {

    }
    try {
      // val resultFuture: Future[_] = { ... }
      //Await.result(resultFuture, Duration.Inf)
      //lines.foreach(Predef.println _)
    } //finally db.close
    // do nothing
  }
}
