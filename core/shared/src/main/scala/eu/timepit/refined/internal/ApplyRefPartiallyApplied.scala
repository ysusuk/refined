package eu.timepit.refined
package internal

import eu.timepit.refined.api.{ RefType, Validate }

@deprecated("", "0.6.0")
final class ApplyRefPartiallyApplied[FTP] {

  @deprecated("", "0.6.0")
  def apply[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): Either[String, FTP] =
    rt.refine[P](t).right.map(ev)

  @deprecated("", "0.6.0")
  def unsafeFrom[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP =
    ev(rt.refine[P].unsafeFrom(t))
}
