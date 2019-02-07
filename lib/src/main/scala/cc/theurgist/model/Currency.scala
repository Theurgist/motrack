package cc.theurgist.model

/**
  * Any countable money entity should be represented as this
  */
case class Currency (
    name: String,
    country: Option[String],
    isCrypto: Boolean
)
