package eu.timepit.refined

import eu.timepit.refined.TestUtils.wellTyped
import eu.timepit.refined.api.Validate
import eu.timepit.refined.generic.Eval
import org.scalacheck.Prop._
import org.scalacheck.Properties
import scala.tools.reflect.ToolBoxError
import shapeless.test.illTyped

class GenericValidateSpecJvm extends Properties("GenericValidate") {

  type IsEven = Eval["(x: Int) => x % 2 == 0"]

  property("Eval.isValid") = {
    val v = Validate[Int, IsEven]
    forAll { (i: Int) =>
      v.isValid(i) ?= (i % 2 == 0)
    }
  }

  property("Eval.showExpr") = secure {
    Validate[Int, IsEven].showExpr(0) ?= "(x: Int) => x % 2 == 0"
  }

  property("Eval.refineMV") = wellTyped {
    refineMV[IsEven](2)
    illTyped("refineMV[IsEven](3)", "Predicate.*fail.*")
  }

  property("Eval.refineV.no parameter type") = {
    val v = Validate[List[Int], Eval["_.headOption.fold(false)(_ > 0)"]]
    forAll { (l: List[Int]) =>
      v.isValid(l) ?= l.headOption.fold(false)(_ > 0)
    }
  }

  property("Eval.refineMV.scope") = wellTyped {
    val two = 2
    illTyped(
      """refineMV[Eval["(x: Int) => x >= two"]](2)""",
      "exception during macro expansion.*"
    )
  }

  property("Eval.refineV.scope") = secure {
    val two = 2
    throws(classOf[ToolBoxError])(refineV[Eval["(x: Int) => x >= two"]](two))
  }
}
