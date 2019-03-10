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

val versions = Map(
  "typesafe-config" -> "1.3.3",
  "logback"         -> "1.3.0-alpha4",
  "scala-logging"   -> "3.9.2",
  "scalafx"         -> "11-R16",
)

// Add JavaFX dependencies
libraryDependencies ++= {
  lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _                            => throw new Exception("Unknown platform!")
  }
  javaFXModules.map(m => "org.openjfx" % s"javafx-$m" % "11" classifier osName)
}

libraryDependencies ++= Seq(
  "org.scalafx"                %% "scalafx"             % versions("scalafx"),
  "org.scalafx"                %% "scalafxml-core-sfx8" % "0.4",
  "com.typesafe"               % "config"               % versions("typesafe-config"),
  "com.typesafe.scala-logging" %% "scala-logging"       % versions("scala-logging"),
  "ch.qos.logback"             % "logback-classic"      % versions("logback"),
  // Test suites dependencies
  "org.scalatest"  %% "scalatest"   % "3.0.5"  % "test",
  "org.scalacheck" %% "scalacheck"  % "1.13.4" % "test",
  "org.mockito"    % "mockito-core" % "1.9.5"  % "test",
)

dependencyOverrides ++= Seq(
  "org.slf4j"   % "slf4j-api" % "1.8.0-beta1",
  "org.scalafx" %% "scalafx"  % versions("scalafx"),
)

// Needed for ScalaFX
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
