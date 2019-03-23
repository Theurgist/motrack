name := "quill-crud"

scalacOptions ++= Seq (
  //"-Ymacro-debug-lite"
)

val versions = new {
  val quill = "3.1.0"
  val logback = "1.3.0-alpha4"
  val h2 = "1.4.197"
}

libraryDependencies ++= {

  Seq(
    "ch.qos.logback"              % "logback-classic" % versions.logback,

    "io.getquill"                 %% "quill-sql"      % versions.quill,
    "io.getquill"                 %% "quill-jdbc"     % versions.quill,
    "com.h2database"              %  "h2"             % versions.h2,

    "org.scala-lang"              % "scala-reflect"   % scalaVersion.value,
    "org.scala-lang"              % "scala-compiler"  % scalaVersion.value,
  )
}

dependencyOverrides ++= Seq(
  "org.slf4j"                  % "slf4j-api"      % "1.8.0-beta1",
)