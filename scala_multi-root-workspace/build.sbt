lazy val root = (project in file("."))
  .aggregate(projectA, projectB)
  .settings(
    name := "root-project",
    scalaVersion := "2.13.12",
    // want to define a Main class for the root project, include it here
    mainClass := Some("root.Main")
  )

lazy val projectA = (project in file("project-a"))
  .settings(
    name := "project-a",
    scalaVersion := "2.13.12"
  )

lazy val projectB = (project in file("project-b"))
  .settings(
    name := "project-b",
    scalaVersion := "2.13.12"
  )
