lazy val commonSettings = Seq(
  organization := "сс.theurgist",
  version := "0.0.0-SNAPSHOT",
  scalaVersion := "2.12.8"
)

lazy val `lib` = (project in file("lib"))
  .settings(commonSettings)

lazy val `jfx-client` = (project in file("jfx-client"))
  .settings(commonSettings)
  .dependsOn(lib)
lazy val server = (project in file("server"))
  .settings(commonSettings)
  .dependsOn(lib)
lazy val `tg-bot` = (project in file("tg-bot"))
  .settings(commonSettings)
  .dependsOn(lib)


lazy val motrack = (project in file("."))
  .aggregate(`jfx-client`, server, `tg-bot`)
  .settings(commonSettings ++ Seq(
    name := "motrack"
  ))

//mainClass in (Compile, run) := (mainClass in Compile in `jfx-client`).value