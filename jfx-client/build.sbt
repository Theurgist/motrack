name := "jfx-client"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
mainClass in (Compile, run) := Some("cc.theurgist.Client")

val versions = new {
  val `typesafe-config` = "1.3.3"
  val logback = "1.3.0-alpha4"
  val `scala-logging` = "3.9.2"

  val scalafx = "11-R16"
  val akka = "2.5.22"
  val `akka-http` = "10.1.8"
  val cats = "1.6.0"
  val `cats-effect` = "1.1.0"
  val circe = "0.11.1"

  val scalatest = "3.2.0-SNAP10"
  val scalacheck = "1.14.0"
  val mockito = "2.24.0"
}


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
  "org.scalafx" %% "scalafx" % versions.scalafx,
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4",

  "com.typesafe" % "config" % versions.`typesafe-config`,
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`,
  "ch.qos.logback" % "logback-classic" % versions.logback,

  "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
  "com.typesafe.akka" %% "akka-actor" % versions.akka,
  "com.typesafe.akka" %% "akka-slf4j" % versions.akka,
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
)

dependencyOverrides ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.8.0-beta1",
  "org.scalafx" %% "scalafx" % versions.scalafx,
)

// Needed for ScalaFX
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")