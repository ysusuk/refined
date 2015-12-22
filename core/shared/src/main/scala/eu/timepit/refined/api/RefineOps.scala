package eu.timepit.refined
package api

import eu.timepit.refined.internal.RefineMFullyApplied

class RefineOps[F[_, _], T, P](implicit rt: RefType[F], v: Validate[T, P]) {

  type RefinedType = F[T, P]

  def refine(t: T): Either[String, RefinedType] =
    rt.refine[P](t)

  def refineM: RefineMFullyApplied[F, T, P] =
    rt.refineMF[T, P]
}
