name := "lib"

val versions = new {
  val `typesafe-config` = "1.3.3"
  val logback = "1.3.0-alpha4"
  val `scala-logging` = "3.9.2"
  val circe = "0.11.1"

  val scalatest = "3.2.0-SNAP10"
  val scalacheck = "1.14.0"
  val mockito = "2.24.0"
}

libraryDependencies ++= Seq(
  "com.typesafe" % "config"          % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",

  "io.circe" %% "circe-core" % versions.circe,
  "io.circe" %% "circe-generic" % versions.circe,
  "io.circe" %% "circe-generic-extras" % versions.circe,
  "io.circe" %% "circe-java8" % versions.circe,

  // Test suites dependencies
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
  "org.mockito" % "mockito-core" % versions.mockito % "test",
)

dependencyOverrides ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.8.0-beta1",
)
