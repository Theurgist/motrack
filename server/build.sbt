name := "server"

val versions = new {
  val `typesafe-config` = "1.3.3"
  val flyway = "5.2.4"
  val h2 = "1.4.197"
  val quill = "3.1.0"
  val logback = "1.3.0-alpha4"
  val `scala-logging` = "3.9.2"
  val hikari = "3.3.1"
  val `akka-http` = "10.1.8"
  val `akka-stream` = "2.5.21"
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
    "com.typesafe" % "config" % versions.`typesafe-config`,
    "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`,
    "ch.qos.logback" % "logback-classic" % versions.logback,

    "org.flywaydb" % "flyway-core" % versions.flyway,
    "io.getquill" %% "quill-sql" % versions.quill,
    "io.getquill" %% "quill-jdbc" % versions.quill,
    "com.zaxxer" % "HikariCP" % versions.hikari,
    "com.h2database" % "h2" % versions.h2,

    "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
    //"com.typesafe.akka" %% "akka-stream" % versions.`akka-stream`,
    "de.heikoseeberger" %% "akka-http-circe" % "1.25.2",
    "io.circe" %% "circe-core" % versions.circe,
    "io.circe" %% "circe-generic" % versions.circe,
    "io.circe" %% "circe-generic-extras" % versions.circe,

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
  "org.slf4j"                  % "slf4j-api"      % "1.8.0-beta1",
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`
)
//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")