import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}
import org.scalajs.sbtplugin.ScalaJSCrossVersion

name := "scala-dom-extender root project"

scalaVersion in ThisBuild := "2.12.7"

lazy val root = project.in(file(".")).
		aggregate(extJS, extJVM).
		settings(
			publish := {},
			publishLocal := {}
		)

lazy val ext = crossProject(JSPlatform, JVMPlatform).
		crossType(CrossType.Full).
		in(file(".")).
		settings(
			name := "foo",
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
