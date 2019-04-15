package cc.theurgist.motrack.server.routes

import akka.http.scaladsl.server.Route

import scala.language.implicitConversions

/**
  * Akka route DSL part construct
  */
trait RouteBranch {
  def route: Route
}

object RouteBranch {

  /**
    * Allows transparent route infusion
    */
  implicit def toRoute(rb: RouteBranch): Route = rb.route
}
