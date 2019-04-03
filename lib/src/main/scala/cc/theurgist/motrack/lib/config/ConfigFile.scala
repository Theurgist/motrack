package cc.theurgist.motrack.lib.config

import com.typesafe.config.ConfigFactory

/**
  * Configuration file wrapper
  *
  * @param fileName path of file at resources folder
  */
class ConfigFile(fileName: String) extends ConfigBranch(ConfigFactory.parseResources(fileName).resolve())
