lazy val commonSettings = Seq(
  organization := "сс.theurgist",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-target:jvm-1.8",
    "-deprecation",
    "-unchecked",
    "-feature",
    "-explaintypes", // Explain type errors in more detail
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access
    //"-Xdev", // Indicates user is a developer - issue warnings about anything which seems amiss
  )
)

lazy val `lib` = (project in file("lib"))
  .settings(commonSettings)
lazy val `quill-crud` = (project in file("quill-crud"))
  .settings(commonSettings)

lazy val `jfx-client` = (project in file("jfx-client"))
  .settings(commonSettings)
  .dependsOn(lib)
lazy val server = (project in file("server"))
  .settings(commonSettings)
  .dependsOn(lib, `quill-crud`)
lazy val `tg-bot` = (project in file("tg-bot"))
  .settings(commonSettings)
  .dependsOn(lib)

lazy val motrack = (project in file("."))
  .aggregate(lib, `jfx-client`, server, `tg-bot`)
  .settings(commonSettings ++ Seq(
    name := "motrack"
  ))
