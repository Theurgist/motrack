name := "server"

val versions = new {
  val `typesafe-config` = "1.3.3"
  val flyway = "5.2.4"
  val h2 = "1.4.197"
  val quill = "3.1.0"
  val logback = "1.3.0-alpha4"
  val `scala-logging` = "3.9.2"
  val hikari = "3.3.1"
}

libraryDependencies ++= {

  Seq(
    "com.typesafe"                % "config"          % versions.`typesafe-config`,
    "com.typesafe.scala-logging"  %% "scala-logging"  % versions.`scala-logging`,
    "ch.qos.logback"              % "logback-classic" % versions.logback,

    "org.flywaydb"                % "flyway-core"     % versions.flyway,
    "io.getquill"                 %% "quill-sql"      % versions.quill,
    "io.getquill"                 %% "quill-jdbc"     % versions.quill,
    "com.zaxxer"                  % "HikariCP"        % versions.hikari,
    "com.h2database"              % "h2"              % versions.h2,

    "org.scala-lang"              % "scala-reflect"   % scalaVersion.value,

    // Test suites dependencies
    "org.scalatest"  %% "scalatest"   % "3.2.0-SNAP10" % "test",
    "org.scalacheck" %% "scalacheck"  % "1.14.0"       % "test",
    "org.mockito"    % "mockito-core" % "2.24.0"       % "test",
    // when and if here will pe postgresql - "com.opentable.components"    % "otj-pg-embedded" % "0.13.1" % "test",
  )
}

dependencyOverrides ++= Seq(
  "org.slf4j"                  % "slf4j-api"      % "1.8.0-beta1",
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`
)
//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")