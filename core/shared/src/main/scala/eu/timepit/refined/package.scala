package eu.timepit

import eu.timepit.refined.api.{Refined, RefType}
import eu.timepit.refined.internal._
import shapeless.tag.@@

package object refined {

  /**
   * Alias for `[[api.RefType.refine]][P]` with `[[api.Refined]]` as type
   * parameter for `[[api.RefType]]`.
   *
   * Note: `V` stands for '''v'''alue class.
   */
  def refineV[P]: RefinePartiallyApplied[Refined, P] = RefType[Refined].refine[P]

  /**
   * Alias for `[[api.RefType.refine]][P]` with `shapeless.tag.@@` as type
   * parameter for `[[api.RefType]]`.
   *
   * Note: `T` stands for '''t'''ag.
   */
  def refineT[P]: RefinePartiallyApplied[@@, P] = RefType[@@].refine[P]

  /**
   * Alias for `[[api.RefType.refineM]][P]` with `[[api.Refined]]` as type
   * parameter for `[[api.RefType]]`.
   *
   * Note: `M` stands for '''m'''acro and `V` stands for '''v'''alue class.
   */
  def refineMV[P]: RefineMPartiallyApplied[Refined, P] = RefType[Refined].refineM[P]

  /**
   * Alias for `[[api.RefType.refineM]][P]` with `shapeless.tag.@@` as type
   * parameter for `[[api.RefType]]`.
   *
   * Note: `M` stands for '''m'''acro and `T` stands for '''t'''ag.
   */
  def refineMT[P]: RefineMPartiallyApplied[@@, P] = RefType[@@].refineM[P]
}
