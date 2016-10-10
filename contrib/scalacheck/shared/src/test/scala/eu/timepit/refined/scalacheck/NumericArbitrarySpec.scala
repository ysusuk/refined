package eu.timepit.refined
package scalacheck

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.scalacheck.numeric._
import eu.timepit.refined.util.time.Minute
import org.scalacheck.Prop._
import org.scalacheck.Properties
import shapeless.nat._

class NumericArbitrarySpec extends Properties("NumericArbitrary") {

  property("Less.positive") =
    checkArbitraryRefType[Refined, Int, Less[100]]

  property("Less.negative") =
    checkArbitraryRefType[Refined, Int, Less[-100]]

  property("Less.Nat") = checkArbitraryRefType[Refined, Long, Less[_10]]

  property("LessEqual.positive") =
    checkArbitraryRefType[Refined, Int, LessEqual[42]]

  property("LessEqual.negative") =
    checkArbitraryRefType[Refined, Int, LessEqual[-42]]

  property("LessEqual.Nat") = checkArbitraryRefType[Refined, Long, LessEqual[_10]]

  property("Greater.positive") =
    checkArbitraryRefType[Refined, Int, Greater[100]]

  property("Greater.negative") =
    checkArbitraryRefType[Refined, Int, Greater[-100]]

  property("Greater.Nat") = checkArbitraryRefType[Refined, Long, Greater[_10]]

  property("GreaterEqual.positive") =
    checkArbitraryRefType[Refined, Int, GreaterEqual[123456]]

  property("GreaterEqual.negative") =
    checkArbitraryRefType[Refined, Int, GreaterEqual[-123456]]

  property("GreaterEqual.Nat") = checkArbitraryRefType[Refined, Int, GreaterEqual[_10]]

  property("Positive") = checkArbitraryRefType[Refined, Float, Positive]

  property("NonPositive") = checkArbitraryRefType[Refined, Short, NonPositive]

  property("Negative") = checkArbitraryRefType[Refined, Double, Negative]

  property("NonNegative") = checkArbitraryRefType[Refined, Long, NonNegative]

  property("Interval.Open") =
    checkArbitraryRefType[Refined, Int, Interval.Open[-23, 42]]

  property("Interval.Open (0.554, 0.556)") =
    checkArbitraryRefType[Refined, Double, Interval.Open[0.554, 0.556]]

  property("Interval.OpenClosed") =
    checkArbitraryRefType[Refined, Double, Interval.OpenClosed[2.71828, 3.14159]]

  property("Interval.OpenClosed (0.54, 0.56]") =
    checkArbitraryRefType[Refined, Float, Interval.OpenClosed[0.54F, 0.56F]]

  property("Interval.ClosedOpen") =
    checkArbitraryRefType[Refined, Double, Interval.ClosedOpen[-2.71828, 3.14159]]

  property("Interval.ClosedOpen [0.4, 0.6)") =
    checkArbitraryRefType[Refined, Float, Interval.ClosedOpen[0.4F, 0.6F]]

  property("Interval.Closed") =
    checkArbitraryRefType[Refined, Int, Interval.Closed[23, 42]]

  property("Interval.alias") = forAll { m: Minute =>
    m >= 0 && m <= 59
  }

  property("chooseRefinedNum") = {
    type Natural = Int Refined NonNegative
    forAll(chooseRefinedNum(23: Natural, 42: Natural)) { n =>
      n >= 23 && n <= 42
    }
  }
}
