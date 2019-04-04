package cc.theurgist.motrack.lib.model

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import io.circe.java8.time._
import io.circe.{Decoder, Encoder}

//TODO not working
trait Encoders {

  val localDateTimePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

  implicit final val ldtDecoder: Decoder[LocalDateTime] = decodeLocalDateTimeWithFormatter(localDateTimePattern)
  implicit final val ldtEncoder: Encoder[LocalDateTime] = encodeLocalDateTimeWithFormatter(localDateTimePattern)

  val localDatePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
  implicit final val ldDecoder: Decoder[LocalDate] = decodeLocalDateWithFormatter(localDatePattern)
  implicit final val ldEncoder: Encoder[LocalDate] = encodeLocalDateWithFormatter(localDatePattern)
}
