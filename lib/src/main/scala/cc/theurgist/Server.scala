package cc.theurgist

import com.typesafe.scalalogging.StrictLogging

object Server extends App with StrictLogging {
  Thread.currentThread().setName("SRV")
  logger.info("Motrack server has been started")
}
