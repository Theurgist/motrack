name := "server"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-unchecked",
  "-Xcheckinit",
  //"-Ypartial-unification", // 2.11.9+
)

val versions = Map(
  "typesafe-config" -> "1.3.3",
  "flyway"          -> "5.2.4",
  "h2"              -> "1.4.197",
  "quill"           -> "3.1.0",
  "logback"         -> "1.3.0-alpha4",
  "scala-logging"   -> "3.9.2",
)

libraryDependencies ++= {

  Seq(
    "com.typesafe"                % "config"          % versions("typesafe-config"),
    "com.typesafe.scala-logging"  %% "scala-logging"  % versions("scala-logging"),
    "ch.qos.logback"              % "logback-classic" % versions("logback"),

    "org.flywaydb"                % "flyway-core"     % versions("flyway"),
    "io.getquill"                 %% "quill-sql"      % versions("quill"),
    "io.getquill"                 %% "quill-jdbc"     % versions("quill"),
    "com.zaxxer"                  % "HikariCP"        % "3.3.1",
    "com.h2database"              % "h2"              % versions("h2"),

    // Test suites dependencies
    "org.scalatest"  %% "scalatest"   % "3.2.0-SNAP10" % "test",
    "org.scalacheck" %% "scalacheck"  % "1.14.0"       % "test",
    "org.mockito"    % "mockito-core" % "2.24.0"       % "test",
    // when and if here will pe postgresql - "com.opentable.components"    % "otj-pg-embedded" % "0.13.1" % "test",
  )
}

dependencyOverrides ++= Seq(
  "org.slf4j"                  % "slf4j-api"      % "1.8.0-beta1",
  "com.typesafe.scala-logging" %% "scala-logging" % versions("scala-logging")
)
//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")