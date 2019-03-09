name := "server"

//scalacOptions += "-Ypartial-unification" // 2.11.9+


lazy val doobieVersion = "0.6.0"


libraryDependencies ++= {

  val versions = Map(
    "flyway" -> "5.2.4",
    "h2" -> "1.4.197",
    "logback" -> "1.3.0-alpha4",
    "scala-logging" -> "3.9.2",

    "doobie" -> "0.6.0"
  )


  Seq(
    "com.typesafe"                %  "config"           % "1.3.3",
    "com.typesafe.scala-logging"  %% "scala-logging"    % versions("scala-logging"),
    "ch.qos.logback"              %  "logback-classic"  % versions("logback"),

    "com.typesafe.slick" %% "slick-hikaricp"  % "3.3.0",
    "com.typesafe.slick" %% "slick"           % "3.3.0",

    "org.flywaydb" % "flyway-core" % versions("flyway"),
    //  "org.xerial" % "sqlite-jdbc" % "3.25.2",
    "com.h2database"  % "h2"      % versions("h2"),
    "org.hsqldb"      % "hsqldb"  % "2.4.1",

    // Start with this one
    "org.tpolecat"    %% "doobie-core"        % versions("doobie"),
    "org.tpolecat"    %% "doobie-postgres"    % versions("doobie"),


    // Test suites dependencies
    "org.scalatest"   %% "scalatest"          % "3.2.0-SNAP10" % "test",
    "org.scalacheck"  %% "scalacheck"         % "1.14.0" % "test",
    "org.mockito"     %  "mockito-core"       % "2.24.0" % "test",
    "org.tpolecat"    %% "doobie-specs2"      % versions("doobie") % "test",
    "org.tpolecat"    %% "doobie-scalatest"   % versions("doobie") % "test", // ScalaTest support for typechecking statements.
  )
}


//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")