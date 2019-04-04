package cc.theurgist.motrack.server.routes.info

import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

import cc.theurgist.motrack.lib.model.Encoders
import cc.theurgist.motrack.server.config.SrvConfig

case class ServerStatus
(
    version: String,
    time: LocalDateTime,
    zonedTime: ZonedDateTime,
    date: LocalDate,
)

object ServerStatus extends Encoders {
  def apply(): ServerStatus = new ServerStatus(
    SrvConfig.protocolVersion,
    LocalDateTime.now,
    ZonedDateTime.now,
    LocalDate.now
  )
}
