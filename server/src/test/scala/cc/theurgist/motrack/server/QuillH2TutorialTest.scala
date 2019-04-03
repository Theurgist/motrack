package cc.theurgist.motrack.server

import io.getquill._
import org.scalatest.{Matchers, WordSpec}

class QuillH2TutorialTest extends WordSpec with Matchers {
  "quill" should {
    "execute simple queries" in {
      val ctx = new SqlMirrorContext(MirrorSqlDialect, Literal)
      import ctx._

      // A quotation can be a simple value
      val pi: ctx.Quoted[Double] = quote(3.14159)

      // And be used within another quotation
      case class Circle(radius: Float)
      val areas = quote {
        query[Circle].map(c => pi * c.radius * c.radius)
      }
      // Quotations can also contain high-order functions and inline values
      val area = quote { c: Circle =>
        {
          val r2 = c.radius * c.radius
          pi * r2
        }
      }
      val areas2 = quote {
        query[Circle].map(c => area(c))
      }

      // Quill’s normalization engine applies reduction steps before translating the quotation
      // to the target language. The correspondent normalized quotation
      // for both versions of the areas query is:
      val areasNormalized = quote {
        query[Circle].map(c => 3.14159 * c.radius * c.radius)
      }

      // Scala doesn’t have support for high-order functions with type parameters.
      // It’s possible to use method type parameter for this purpose:
      def existsAny[T] = quote { xs: Query[T] => (p: T => Boolean) =>
        xs.filter(p(_)).nonEmpty
      }

      val q = quote {
        query[Circle].filter { c1 =>
          existsAny[Circle](query[Circle])(c2 => c2.radius > c1.radius)
        }
      }

      // Avoid type widening (Quoted[Query[Circle]]), or else the quotation will be dynamic.
      val qDynamic: Quoted[Query[Circle]] = quote {
        query[Circle].filter(c => c.radius > 10)
      }

      val r1 = ctx.run(qDynamic) // Dynamic query

      // Quoting is implicit when writing a query in a run statement.
      ctx.run(query[Circle].map(_.radius))
      // SELECT r.radius FROM Circle r

      // Quotations are designed to be self-contained, without references to runtime values outside their scope.
      // There are two mechanisms to explicitly bind runtime values to a quotation execution.

      // Lifted values
      // A runtime value can be lifted to a quotation through the method lift:
      def biggerThan(i: Float) = quote {
        query[Circle].filter(r => r.radius > lift(i))
      }
      val r2 = ctx.run(biggerThan(10)) // SELECT r.radius FROM Circle r WHERE r.radius > ?

      // Lifted queries
      //  A Traversable instance can be lifted as a Query. There are two main usages for lifted queries:

      //  contains
      def find(radiusList: List[Float]) = quote {
        query[Circle].filter(r => liftQuery(radiusList).contains(r.radius))
      }
      val r3 = ctx.run(find(List(1.1F, 1.2F)))
      // SELECT r.radius FROM Circle r WHERE r.radius IN (?)

      //  batch action
      def insert(circles: List[Circle]) = quote {
        liftQuery(circles).foreach(c => query[Circle].insert(c))
      }
      val r4 = ctx.run(insert(List(Circle(1.1F), Circle(1.2F))))
      // INSERT INTO Circle (radius) VALUES (?)

      r4
    }

  }
}
