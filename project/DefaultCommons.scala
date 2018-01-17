import sbt._
import Keys._

object DefaultCommons {

  object Settings {

    private val myOrganization = "com.mb"
    private val myScalaVersion = "2.12.4"

    def getDefaultSettings(myName: String, myVersion: String): Seq[Def.Setting[_]] = Seq (

      organization  := myOrganization,
      scalaVersion  := myScalaVersion,
      name          := myName,
      version       := myVersion

    )

  }

  object Dependencies {

    private val scalaTestVersion: String        = "3.0.4"
    private val typeSafeConfigVersion: String   = "1.3.1"
    private val akkaVersion: String             = "2.4.17"
    private val persistenceRedisVersion: String = "0.7.0"
    private val scalaPbVersion: String          = "0.6.6"
    private val circeVersion: String            = "0.9.0"

    private val scalactic         = "org.scalactic" %% "scalactic" % scalaTestVersion
    private val scalatest         = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
    private val typesafe          = "com.typesafe" % "config" % typeSafeConfigVersion
    private val akkaCore          = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    private val akkaCoreTest      = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
    private val akkaPersistence   = "com.typesafe.akka" %% "akka-persistence" % akkaVersion
    private val persistenceRedis  = "com.hootsuite" %% "akka-persistence-redis" % persistenceRedisVersion

    private val circe             = Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion)

    def getDefaultDependencies: Seq[ModuleID] = Seq (

      scalactic,
      scalatest,
      typesafe,
      akkaCore,
      akkaCoreTest,
      akkaPersistence,
      persistenceRedis

    ) ++ circe

  }

}