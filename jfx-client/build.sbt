name := "jfx-client"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xcheckinit",
  "-encoding",
  "utf8",
  "-feature"
)

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
mainClass in (Compile, run) := Some("cc.theurgist.Client")

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")

libraryDependencies ++=
  javaFXModules.map( m => "org.openjfx" % s"javafx-$m" % "11" classifier osName)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "11-R16",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4",

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
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)