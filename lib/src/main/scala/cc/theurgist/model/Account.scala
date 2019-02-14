package cc.theurgist.model

import cc.theurgist.model.AccountType.AccountType

/**
  * Money deposit entry
  */
case class Account (
    ownerId: String,
    name: String,
    accType: AccountType,
    createdAt: Long,
    currencyCode: String,

    id: Option[String]
)
