name := """ceibaestacionamiento"""
organization := "juanzapata"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  ws,
  guice,
  filters,
  specs2 % "provided",
  "com.typesafe.slick"     %% "slick"              % "3.2.0",
  "org.postgresql"          % "postgresql"         % "42.2.2",
  "org.typelevel"          %% "cats-core"          % "1.1.0"    withSources(),
  "org.typelevel"          %% "cats-kernel"        % "1.1.0"    withSources(),
  "org.typelevel"          %% "cats-macros"        % "1.1.0"    withSources(),
  "com.typesafe.slick"     %% "slick-hikaricp"     % "3.2.0",
  "io.monix"               %% "monix"              % "3.0.0-RC2",
  "com.h2database"          % "h2"                 % "1.4.198" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"   % Test,
  "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
)
