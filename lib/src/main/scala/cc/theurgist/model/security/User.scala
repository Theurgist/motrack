package cc.theurgist.model.security

import cc.theurgist.model.WithId

/**
  * End user
  */
case class User(
    id: Int,
    login: String,
    name: String,
) extends WithId[Int]
