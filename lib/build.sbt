name := "lib"

libraryDependencies ++= Seq(
  "com.typesafe"               % "config"          % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.2",
  "ch.qos.logback"             % "logback-classic" % "1.3.0-alpha4",
  // Test suites dependencies
  "org.scalatest"  %% "scalatest"   % "3.0.5"  % "test",
  "org.scalacheck" %% "scalacheck"  % "1.13.4" % "test",
  "org.mockito"    % "mockito-core" % "1.9.5"  % "test",
)

dependencyOverrides ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.8.0-beta1",
)
