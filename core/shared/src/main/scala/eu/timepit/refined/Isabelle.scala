package eu.timepit.refined

import scala.concurrent.{ Future, Await }
import scala.concurrent.ExecutionContext.Implicits.global
import edu.tum.cs.isabelle.api.Version
import edu.tum.cs.isabelle.setup.Setup

import scala.concurrent.duration.Duration

object Isabelle extends App {

  val version = Version("2014")
  val setup = Setup.defaultSetup(version)
  val i = setup.flatMap(_.makeEnvironment)

  val x = Await.result(i, Duration.Inf)
  println(x)

}
