/**
  * Root
  */
lazy val appRoot = project.in(file("."))

  .settings(DefaultCommons.Settings.getDefaultSettings(myName = "Prototype-Akka-Persistence-Serializer", myVersion = "0.0.1"))
  .settings( libraryDependencies ++= DefaultCommons.Dependencies.getDefaultDependencies )