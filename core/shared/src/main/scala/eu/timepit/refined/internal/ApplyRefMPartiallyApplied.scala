package eu.timepit.refined
package internal

import eu.timepit.refined.api.{ RefType, Validate }

@deprecated("", "0.6.0")
final class ApplyRefMPartiallyApplied[FTP] {

  @deprecated("", "0.6.0")
  def apply[F[_, _], T, P](t: T)(
    implicit
    ev: F[T, P] =:= FTP, rt: RefType[F], v: Validate[T, P]
  ): FTP = macro macros.RefineMacro.implApplyRef[FTP, F, T, P]
}
