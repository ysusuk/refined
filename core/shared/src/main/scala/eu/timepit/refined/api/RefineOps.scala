package eu.timepit.refined
package api

trait RefineOps[FTP] {

  def refine[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): Either[String, FTP] =
    rt.refine[P](t).right.map(ev)

  def refineM[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP = macro macros.RefineMacro.implApplyRef[FTP, F, T, P]

  def refineUnsafe[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP =
    ev(rt.refine[P].unsafeFrom(t))
}

object RefineOps {

  def apply[FTP]: RefineOps[FTP] = new RefineOps[FTP] {}
}
