package cc.theurgist.motrack.lib.dto

import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

import cc.theurgist.motrack.lib.config.CommonConfig
import cc.theurgist.motrack.lib.model.Encoders

case class ServerStatus(
    health: Health,
    version: String,
    time: LocalDateTime,
    zonedTime: ZonedDateTime,
    date: LocalDate,
)

object ServerStatus extends Encoders {
  def apply(): ServerStatus = new ServerStatus(
    Green,
    CommonConfig.protocolVersion,
    LocalDateTime.now,
    ZonedDateTime.now,
    LocalDate.now
  )

}

sealed trait Health
case object Red    extends Health
case object Yellow extends Health
case object Green  extends Health
