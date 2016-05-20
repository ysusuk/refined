package eu.timepit.refined
package internal

import eu.timepit.refined.api.{ RefType, Validate }
import monocle.Prism

/**
 * Helper class that allows the types `F`, `T`, and `P` to be inferred
 * from calls like `[[api.RefType.applyRef]][F[T, P]](t)`.
 *
 * See [[http://tpolecat.github.io/2015/07/30/infer.html]] for a detailed
 * explanation of this trick.
 */
final class ApplyRefPartiallyApplied[FTP] {

  def apply[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): Either[String, FTP] =
    rt.refine[P](t).right.map(ev)

  def prism[F[_, _], T, P](
    implicit
    ev1: F[T, P] =:= FTP, ev2: FTP =:= F[T, P], rt: RefType[F], v: Validate[T, P]
  ): Prism[T, FTP] =
    Prism((t: T) => rt.refine[P](t).right.toOption.map(ev1))(tp => rt.unwrap(tp))

  /*

scala> type PosInt = Int Refined Positive
defined type alias PosInt

scala> val p = RefType.applyRef[PosInt].prism
p: monocle.Prism[Int,PosInt] = monocle.Prism$$anon$7@6960f5b

scala> p.getOption(4)
res0: Option[PosInt] = Some(4)

scala> p.getOption(-4)
res1: Option[PosInt] = None

scala> p.reverseGet(5: PosInt)
res4: Int = 5

  */
}
