package cc.theurgist.model

import cc.theurgist.model.AccountType.AccountType

/**
  * Money deposit entry
  */
case class Account(
    id: Long,
    ownerId: String,
    name: String,
    accType: AccountType,
    createdAt: Long,
    currencyCode: String
) extends WithId[Long]
