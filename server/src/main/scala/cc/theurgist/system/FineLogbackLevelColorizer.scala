package cc.theurgist.system

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.{ANSIConstants, ForegroundCompositeConverterBase}

/**
  * Custom Logback colorizer for event type
  * Should be enabled in logback XML configuration file with this line:
  * <conversionRule conversionWord="lvlclrz" converterClass="util.FineLogbackLevelColorizer" />
  * Pattern usage example:
  * %lvlclrz(%-5level)
  */
class FineLogbackLevelColorizer extends ForegroundCompositeConverterBase[ILoggingEvent] {

  override protected def getForegroundColorCode(event: ILoggingEvent): String =
    if (event != null) {
      event.getLevel.toInt match {
        case Level.TRACE_INT =>
          ANSIConstants.BOLD + ANSIConstants.BLUE_FG // same as default color scheme

        case Level.ERROR_INT =>
          ANSIConstants.BOLD + ANSIConstants.RED_FG // same as default color scheme
        case Level.WARN_INT =>
          ANSIConstants.RED_FG // same as default color scheme
        case Level.INFO_INT =>
          ANSIConstants.CYAN_FG // use CYAN instead of BLUE

        case _ =>
          ANSIConstants.DEFAULT_FG
      }
    }
    else ANSIConstants.DEFAULT_FG
}
