name := "jfx-client"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
mainClass in (Compile, run) := Some("cc.theurgist.Client")

val versions = new {
  val `typesafe-config` = "1.3.3"
  val logback = "1.3.0-alpha4"
  val `slf4j-api` = "1.8.0-beta1"
  val `scala-logging` = "3.9.2"

  val javafx = "11.0.1"
  val scalafx = "11-R16"
  val akka = "2.5.22"
  val `akka-http` = "10.1.8"
  val `akka-http-circe` = "1.25.2"
  val cats = "1.6.0"
  val `cats-effect` = "1.1.0"
  val circe = "0.11.1"

  val scalatest = "3.2.0-SNAP10"
  val scalacheck = "1.14.0"
  val mockito = "2.24.0"
}

// Add JavaFX dependencies

val javaFXModules = {
  lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _                            => throw new Exception("Unknown platform!")
  }
  javaFXModules.map(m => "org.openjfx" % s"javafx-$m" % versions.javafx classifier osName)
}
libraryDependencies ++= javaFXModules
dependencyOverrides ++= javaFXModules

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % versions.scalafx,
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.4",

  "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
  "com.typesafe.akka" %% "akka-actor" % versions.akka,
  "com.typesafe.akka" %% "akka-slf4j" % versions.akka,
  "de.heikoseeberger" %% "akka-http-circe" % versions.`akka-http-circe`,

  "org.typelevel" %% "cats-core" % versions.cats,
  "org.typelevel" %% "cats-effect" % versions.`cats-effect`,

  // Test suites dependencies
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.scalacheck" %% "scalacheck" % versions.scalacheck % "test",
  "org.mockito" % "mockito-core" % versions.mockito % "test",
)

dependencyOverrides ++= Seq(
  "org.slf4j" % "slf4j-api" % versions.`slf4j-api`,
  "com.typesafe.scala-logging" %% "scala-logging" % versions.`scala-logging`,
  "org.typelevel" %% "cats-core" % versions.cats,
  "com.typesafe.akka" %% "akka-actor" % versions.akka,
  "com.typesafe.akka" %% "akka-http" % versions.`akka-http`,
  "org.scalafx" %% "scalafx" % versions.scalafx,
)

//excludeDependencies ++= Seq(
//  // commons-logging is replaced by jcl-over-slf4j
//  ExclusionRule("commons-logging", "commons-logging")
//)


addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full) // Needed for ScalaFX
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")


assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}