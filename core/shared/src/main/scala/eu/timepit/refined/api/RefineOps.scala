package eu.timepit.refined
package api

/**
 *
 */
trait RefineOps[FTP] {

  /**
   * Returns a value of type `T` refined as `FTP` on the right if it
   * satisfies the predicate `P`, or an error message on the left
   * otherwise.
   *
   * Example: {{{
   * scala> import eu.timepit.refined.api.{ Refined, RefineOps }
   *      | import eu.timepit.refined.numeric.Positive
   *
   * scala> type PosInt = Int Refined Positive
   * scala> RefineOps[PosInt].refine(10)
   * res0: Either[String, PosInt] = Right(10)
   * }}}
   */
  def refine[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): Either[String, FTP] =
    rt.refine[P](t).right.map(ev)

  /**
   * Macro that returns a value of type `T` refined as `FTP` if  it
   * satisfies the predicate `P`, or fails to compile otherwise.
   *
   * Example: {{{
   * scala> import eu.timepit.refined.api.{ Refined, RefineOps }
   *      | import eu.timepit.refined.numeric.Positive
   *
   * scala> type PosInt = Int Refined Positive
   * scala> RefineOps[PosInt].refineM(10)
   * res0: PosInt = 10
   * }}}
   *
   * Note: `M` stands for '''m'''acro.
   */
  def refineM[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP = macro macros.RefineMacro.implApplyRef[FTP, F, T, P]

  /**
   * Example: {{{
   * scala> import eu.timepit.refined.api.{ Refined, RefineOps }
   *      | import eu.timepit.refined.numeric.Positive
   *
   * scala> type PosInt = Int Refined Positive
   * scala> RefineOps[PosInt].refineUnsafe(10)
   * res0: PosInt = 10
   * }}}
   */
  def refineUnsafe[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP =
    ev(rt.refine[P].unsafeFrom(t))
}

object RefineOps {

  /** Returns a `RefineOps` for the given refined type `FTP`. */
  def apply[FTP]: RefineOps[FTP] = new RefineOps[FTP] {}
}
