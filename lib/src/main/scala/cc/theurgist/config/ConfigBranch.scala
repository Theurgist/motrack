package cc.theurgist.config
import com.typesafe.config.Config

/**
  * Configuration branch wrapper
  *
  * @param c base typesafe config object
  */
class ConfigBranch(protected val c: Config) {

  /**
    * GEt subbranch of this branch
    *
    * @param path path relative to this configuration branch
    * @return ConfigBranch object
    */
  def branch(path: String) = new ConfigBranch(c.getConfig(path))

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

}
