val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.knowm.xchart" % "xchart" % "3.8.0",
      "org.jfree" % "jfreechart" % "1.5.3",
      "org.jfree" % "jcommon" % "1.0.23",
      "org.apache.commons" % "commons-math3" % "3.6.1",
      "org.jfree" % "jfreechart" % "1.5.3"
    )
  )
