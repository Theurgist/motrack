name := "tg-bot"

val versions = new {
  val `slf4j-api` = "1.8.0-beta1"
  val `scala-logging` = "3.9.2"

  val scalatest = "3.2.0-SNAP10"
  val scalacheck = "1.14.0"
  val mockito = "2.24.0"
}

libraryDependencies ++= Seq(
  // Test suites dependencies
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
  "org.mockito" % "mockito-core" % versions.mockito % "test",
)

dependencyOverrides ++= Seq(
  "org.slf4j" % "slf4j-api" % versions.`slf4j-api`,
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`,
)

assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}