package eu.timepit.refined

import eu.timepit.refined.TestUtils._
import eu.timepit.refined.collection.Contains
import eu.timepit.refined.generic._
import org.scalacheck.Prop._
import org.scalacheck.Properties
import shapeless.Nat._
import shapeless.test.illTyped

class GenericValidateSpec extends Properties("GenericValidate") {

  property("Equal.isValid") = secure {
    isValid[Equal[1.4]](1.4)
  }

  property("Equal.notValid") = secure {
    notValid[Equal[1.4]](2.4)
  }

  property("Equal.showExpr") = secure {
    showExpr[Equal[1.4]](0.4) ?= "(0.4 == 1.4)"
  }

  property("Equal.object.isValid") = secure {
    object Foo
    isValid[Equal[Foo.type]](Foo)
  }

  property("Equal.Symbol.isValid") = secure {
    isValid[Equal['foo]]('foo)
  }

  property("Equal.Symbol.notValid") = secure {
    notValid[Equal['foo]]('bar)
  }

  property("Equal.Symbol.showExpr") = secure {
    showExpr[Equal['foo]]('bar) ?= "('bar == 'foo)"
  }

  property("Equal.Nat.Int.isValid") = forAll { (i: Int) =>
    isValid[Equal[_0]](i) ?= (i == 0)
  }

  property("Equal.Nat.Double.isValid") = forAll { (d: Double) =>
    isValid[Equal[_0]](d) ?= (d == 0.0)
  }

  property("Equal.Nat.showExpr") = secure {
    showExpr[Equal[_5]](0) ?= "(0 == 5)"
  }

  property("Equal.Nat ~= Equal.Int") = forAll { (i: Int) =>
    showResult[Equal[_1]](i) ?= showResult[Equal[1]](i)
  }

  property("ConstructorNames.isValid") = secure {
    isValid[ConstructorNames[Contains["Some"]]](Option(0))
  }

  property("ConstructorNames.notValid") = secure {
    notValid[ConstructorNames[Contains["Just"]]](Option(0))
  }

  property("ConstructorNames.showExpr") = secure {
    showExpr[ConstructorNames[Contains["Just"]]](Option(0)) ?=
      "!(!(None == Just) && !(Some == Just))"
  }

  property("FieldNames.isValid") = secure {
    case class A(fst: Any = 1, snd: Any = 2)
    isValid[FieldNames[Contains["snd"]]](A())
  }

  property("FieldNames.notValid") = secure {
    case class A(fst: Any = 1, snd: Any = 2)
    notValid[FieldNames[Contains["first"]]](A())
  }

  property("FieldNames.showExpr") = secure {
    case class A(fst: Any = 1, snd: Any = 2)
    showExpr[FieldNames[Contains["third"]]](A()) ?=
      "!(!(fst == third) && !(snd == third))"
  }

  property("Subtype.isValid") = secure {
    isValid[Subtype[AnyVal]](0)
  }

  property("Subtype.noInstance") = wellTyped {
    illTyped("isValid[Subtype[Int]](0: AnyVal)", ".*could not find implicit value.*")
  }

  property("Supertype.isValid") = secure {
    isValid[Supertype[List[Int]]](Seq(0))
  }

  property("Supertype.noInstance") = wellTyped {
    illTyped("isValid[Supertype[Seq[Int]]](List(0))", ".*could not find implicit value.*")
  }
}
