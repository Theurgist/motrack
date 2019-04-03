package cc.theurgist.motrack.lib.config

import com.typesafe.config.{Config, ConfigException}

/**
  * Configuration branch wrapper
  *
  * @param c base typesafe config object
  */
class ConfigBranch(protected val c: Config, val path: String) {
  def this(c: Config) = this(c, "")

  /**
    * Get subbranch of this branch
    *
    * @param path path relative to this configuration branch
    * @return ConfigBranch object
    */
  def branch(path: String) = new ConfigBranch(
    c.getConfig(path),
    if (this.path.isEmpty) path else s"${this.path}.$path"
  )

  /**
    * Read configuration key as an Option[T]
    *
    * @param path path relative to this configuration branch
    * @tparam T desired unwrapped result type
    * @return optional key value or None if there is none
    */
  def opt[T](path: String): Option[T] =
    if (c.hasPath(path))
      Some(c.getAnyRef(path).asInstanceOf[T])
    else
      None

  /**
    * Read configuration key as instance of T
    *
    * @param path path relative to this configuration branch
    * @tparam T desired result type
    * @return key value or null if there is none
    */
  def readNullable[T](path: String): T =
    if (c.hasPath(path))
      c.getAnyRef(path).asInstanceOf[T]
    else
      null.asInstanceOf[T]

  /**
    * Read configuration key as instance of T
    *
    * @param path path relative to this configuration branch
    * @tparam T desired result type
    * @return key value
    * @throws ConfigException on missing key
    */
  def read[T](path: String): T = c.getAnyRef(path).asInstanceOf[T]

}
