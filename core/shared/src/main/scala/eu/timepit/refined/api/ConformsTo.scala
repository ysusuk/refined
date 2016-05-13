package eu.timepit.refined
package api

import eu.timepit.refined.macros.ConformsToMacro
import shapeless.Witness

trait ConformsTo[S, P]

object ConformsTo {

  implicit def witnessConformsTo[T, S <: T, P](
    implicit
    ws: Witness.Aux[S], v: Validate[T, P]
  ): ConformsTo[S, P] = macro ConformsToMacro.impl[T, S, P]
}
