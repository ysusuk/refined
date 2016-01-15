package eu.timepit.refined
package macros

import eu.timepit.refined.api.{ RefType, Validate }
import eu.timepit.refined.internal.Resources
import macrocompat.bundle
import scala.reflect.macros.blackbox

@bundle
class RefineMacro(val c: blackbox.Context) extends MacroUtils {
  import c.universe._

  def impl[F[_, _], T: c.WeakTypeTag, P](t: c.Expr[T])(
    v: c.Expr[Validate[T, P]], rt: c.Expr[RefType[F]]
  ): c.Expr[F[T, P]] = {

    val validate = eval(v)

    val tValue: T = compileTimeConstant(t) match {
      case Some(constant) => constant
      case _ if validate.isConstant => null.asInstanceOf[T]
      case _ => abort(Resources.refineNonCompileTimeConstant)
    }

    val res = validate.validate(tValue)
    if (res.isFailed) {
      abort(validate.showResult(tValue, res))
    }

    reify(rt.splice.unsafeWrap(t.splice))
  }

  def compileTimeConstant[T](t: c.Expr[T]): Option[T] =
    t.tree match {
      case Literal(Constant(value)) => Some(value.asInstanceOf[T])
      case x if isCompileTimeConstant(x) => Some(eval(t))
      case _ => None
    }

  def isCompileTimeConstant(t: Tree): Boolean =
    t match {
      case q"immutable.this.Nil" => true
      case q"$term.apply[$tpe](..$args)" =>
        standardCollections.exists(_ equalsStructure term) && args.forall(isLiteralConstant)
      case _ => false
    }

  val standardCollections: List[Tree] = List(
    q"immutable.this.List",
    q"scala.this.Predef.Set",
    q"scala.`package`.Vector",
    q"scala.collection.immutable.List",
    q"scala.collection.immutable.Set",
    q"scala.collection.immutable.Vector"
  )

  def isLiteralConstant(t: Tree): Boolean =
    PartialFunction.cond(t) { case Literal(Constant(_)) => true }
}
