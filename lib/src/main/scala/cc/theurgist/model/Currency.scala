package cc.theurgist.model

/**
  * Any countable money entity should be represented as this
  */
case class Currency(
    id: Int,
    code: String,
    name: String,
    unicode: String,
    country: Option[String],
    isCrypto: Boolean
) extends Entity with WithId[Int]
