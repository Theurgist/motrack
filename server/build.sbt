name := "server"

//scalacOptions += "-Ypartial-unification" // 2.11.9+


lazy val doobieVersion = "0.6.0"


libraryDependencies ++= Seq(
  "com.typesafe"                %  "config"           % "1.3.3",
  "com.typesafe.scala-logging"  %% "scala-logging"    % "3.9.2",
  "ch.qos.logback"              %  "logback-classic"  % "1.3.0-alpha4",

  "com.typesafe.slick" %% "slick-hikaricp"  % "3.3.0",
  "com.typesafe.slick" %% "slick"           % "3.3.0",

//  "org.flywaydb" % "flyway-maven-plugin" % "5.2.4",
//  "org.xerial" % "sqlite-jdbc" % "3.25.2",
  "com.h2database"  % "h2"      % "1.4.196",
  "org.hsqldb"      % "hsqldb"  % "2.4.1",

  // Start with this one
  "org.tpolecat"    %% "doobie-core"        % doobieVersion,
  "org.tpolecat"    %% "doobie-postgres"    % doobieVersion,

)

// Test suites dependencies
libraryDependencies ++= Seq(
  "org.scalatest"   %% "scalatest"          % "3.2.0-SNAP10" % "test",
  "org.scalacheck"  %% "scalacheck"         % "1.14.0" % "test",
  "org.mockito"     %  "mockito-core"       % "2.24.0" % "test",
  "org.tpolecat"    %% "doobie-specs2"      % doobieVersion % "test",
  "org.tpolecat"    %% "doobie-scalatest"   % doobieVersion % "test", // ScalaTest support for typechecking statements.
)

//addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.2.0")