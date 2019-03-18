import org.scalajs.sbtplugin.ScalaJSCrossVersion

import sbtcrossproject.CrossPlugin.autoImport.{ CrossType, crossProject }

name := "scala-dom-extender"

description in ThisBuild := "Scala library for DOM manipulation."

organization in ThisBuild := "com.greencatsoft"

version in ThisBuild := "0.1-SNAPSHOT"

homepage in ThisBuild := Some(url("http://github.com/greencatsoft/scala-dom-extender"))

licenses in ThisBuild := Seq("Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra in ThisBuild := (
  <scm>
    <url>git@github.com:greencatsoft/scala-dom-extender.git</url>
    <connection>scm:git:git@github.com:greencatsoft/scala-dom-extender.git</connection>
  </scm>
    <developers>
      <developer>
        <id>mmx900</id>
        <name>Soyu Kim</name>
        <url>http://github.com/mmx900</url>
      </developer>
    </developers>
  )

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

pomIncludeRepository := { _ => false }

val scalaSettings = Seq(
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq("-feature", "-deprecation"),
  unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil,
  unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil)

lazy val root = project.in(file(".")).
  settings(
    skip in publish := true
  ).
  aggregate(extJS, extJVM)

lazy val ext = crossProject(JSPlatform, JVMPlatform).
  crossType(CrossType.Full).
  in(file(".")).
  settings(
    name := "scala-dom-extender",
    version := "0.1-SNAPSHOT"
  ).
  jvmSettings(
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.11.3"),
  ).
  jsSettings(
    libraryDependencies ++= Seq(
      "com.greencatsoft" % "scalajs-d3" % "0.3-SNAPSHOT" cross ScalaJSCrossVersion.binary,
      "org.scala-js" % "scalajs-dom" % "0.9.6" cross ScalaJSCrossVersion.binary),
  )

lazy val extJVM = ext.jvm
lazy val extJS = ext.js
