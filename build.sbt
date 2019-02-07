lazy val commonSettings = Seq(
  organization := "сс.theurgist",
  version := "0.0.0-SNAPSHOT",
  scalaVersion := "2.12.8"
)


lazy val `jfx-client` = (project in file("jfx-client"))
  .settings(commonSettings)
lazy val server = (project in file("server"))
  .settings(commonSettings)
lazy val `tg-bot` = (project in file("tg-bot"))
  .settings(commonSettings)


lazy val motrack = (project in file("."))
  .aggregate(`jfx-client`, server, `tg-bot`)
  .settings(commonSettings ++ Seq(
    name := "motrack"
  ))