name := """parqueaderoCeiba"""
organization := "ceibajdl"

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
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1"   % Test,
  "org.mockito"             % "mockito-all" % "1.10.19" % Test,
  "org.specs2" %% "specs2-mock" % "4.6.0" % Test,
  "org.mockito"             %   "mockito-core"     % "1.10.19" % "provided"
//  "org.typelevel"          %%  "cats-core"         % "1.4.0"
  //  "com.typesafe.play" %% "play-test" % "2.5.19"
  //  "org.scalamock"                %%  "scalamock-scalatest-support"  % "3.2.2"                % "provided"

)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
