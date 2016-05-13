package eu.timepit.refined
package macros

import eu.timepit.refined.api.{ ConformsTo, Validate }
import macrocompat.bundle
import scala.reflect.macros.blackbox
import shapeless.Witness

@bundle
class ConformsToMacro(val c: blackbox.Context) extends MacroUtils {
  import c.universe._

  def impl[T, S <: T: c.WeakTypeTag, P: c.WeakTypeTag](
    ws: c.Expr[Witness.Aux[S]], v: c.Expr[Validate[T, P]]
  ): c.Expr[ConformsTo[S, P]] = {
    val s = eval(ws).value
    val validate = eval(v)

    if (validate.notValid(s)) {
      abort("")
    }

    reify(new ConformsTo[S, P] {})
  }
}
