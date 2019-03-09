package cc.theurgist
import cc.theurgist.migration.Migrator
import org.scalatest.WordSpec

class FlywaySetupTest extends WordSpec {

  "h2 database" should {

    "be able to setup" in {
      val m = new Migrator
    }

  }

}
