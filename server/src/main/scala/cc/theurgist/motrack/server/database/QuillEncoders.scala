package cc.theurgist.motrack.server.database

import cc.theurgist.motrack.lib.model.AccountType
import io.getquill.MappedEncoding

object QuillEncoders {
  implicit val encodeAccountType: MappedEncoding[AccountType, Byte] =
    MappedEncoding[AccountType, Byte](_.code)
  implicit val decodeAccountType: MappedEncoding[Byte, AccountType] =
    MappedEncoding[Byte, AccountType](AccountType(_))
}
