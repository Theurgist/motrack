name := "server"

val versions = new {
  val `typesafe-config` = "1.3.3"
  val flyway = "5.2.4"
  val h2 = "1.4.197"
  val quill = "3.1.0"
  val logback = "1.3.0-alpha4"
  val `slf4j-api` = "1.8.0-beta1"
  val `scala-logging` = "3.9.2"
  val hikari = "3.3.1"
  val `akka-http` = "10.1.8"
  val `akka-stream` = "2.5.21"
  val `akka-http-session` = "0.5.6"
  val `akka-http-circe` = "1.25.2"
  val cats = "1.6.0"
  val `cats-effect` = "1.1.0"
  val circe = "0.11.1"

  val scalatest = "3.2.0-SNAP10"
  val scalacheck = "1.14.0"
  val mockito = "2.24.0"
}

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= {

  Seq(
    "org.flywaydb" % "flyway-core" % versions.flyway,
    "io.getquill" %% "quill-sql" % versions.quill,
    "io.getquill" %% "quill-jdbc" % versions.quill,
    "com.zaxxer" % "HikariCP" % versions.hikari,
    "com.h2database" % "h2" % versions.h2,

    "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
    "com.softwaremill.akka-http-session" %% "core" % versions.`akka-http-session`,
    "com.softwaremill.akka-http-session" %% "jwt" % versions.`akka-http-session`,
    "de.heikoseeberger" %% "akka-http-circe" % versions.`akka-http-circe`,

    "org.typelevel" %% "cats-core" % versions.cats,
    "org.typelevel" %% "cats-effect" % versions.`cats-effect`,

    // Test suites dependencies
    "org.scalatest" %% "scalatest" % versions.scalatest % "test",
    "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
    "org.mockito" % "mockito-core" % versions.mockito % "test",
    // when and if here will pe postgresql - "com.opentable.components"    % "otj-pg-embedded" % "0.13.1" % "test",
  )
}

dependencyOverrides ++= Seq(
  "org.typelevel" %% "cats-core" % versions.cats,
  "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
  "org.slf4j" % "slf4j-api" % versions.`slf4j-api`,
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`,
)
//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")


assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}