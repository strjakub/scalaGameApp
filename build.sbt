name := "scala_skoczki"

version := "0.1"

scalaVersion := "2.13.8"

libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R24"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

mainClass := Some("hello.ScalaFXHelloWorld")

fork := true

libraryDependencies ++= {
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "18.0.1" classifier osName)
}
