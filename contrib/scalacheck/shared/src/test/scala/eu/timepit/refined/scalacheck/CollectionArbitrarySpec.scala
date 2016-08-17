package eu.timepit.refined.scalacheck

import eu.timepit.refined.api.Refined
import eu.timepit.refined.char.UpperCase
import eu.timepit.refined.collection.Forall
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.scalacheck.char._
import eu.timepit.refined.scalacheck.collection._
import eu.timepit.refined.scalacheck.numeric._
import org.scalacheck.Properties

class CollectionArbitrarySpec extends Properties("CollectionArbitrarySpec") {

  property("Forall[Positive]") =
    checkArbitraryRefType[Refined, Vector[Int], Forall[Positive]]

  property("Forall[UpperCase]") =
    checkArbitraryRefType[Refined, String, Forall[UpperCase]]
}
