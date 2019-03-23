package cc.theurgist.database

import cc.theurgist.model.AccountType
import io.getquill.MappedEncoding

object QuillEncoders {
  implicit val encodeAccountType: MappedEncoding[AccountType, Byte] =
    MappedEncoding[AccountType, Byte](_.code)
  implicit val decodeAccountType: MappedEncoding[Byte, AccountType] =
    MappedEncoding[Byte, AccountType](AccountType(_))
}
