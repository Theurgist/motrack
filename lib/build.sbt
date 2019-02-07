name := "lib"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",
)

// Test suites dependencies
libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"   % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck"  % "1.13.4" % "test",
  "org.mockito"    % "mockito-core" % "1.9.5" % "test",
)

// Needed for ScalaFX
//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)