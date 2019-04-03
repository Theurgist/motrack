package cc.theurgist.motrack.tgbot

import com.typesafe.scalalogging.StrictLogging

object BotMain extends App with StrictLogging {
  Thread.currentThread().setName("BOT")
  logger.info("Telegram bot has been started")
}
