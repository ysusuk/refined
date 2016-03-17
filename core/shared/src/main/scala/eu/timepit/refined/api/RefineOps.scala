package eu.timepit.refined
package api

import eu.timepit.refined.internal.RefineMFullyApplied

import scala.annotation.StaticAnnotation

class RefineOps[F[_, _], T, P](implicit rt: RefType[F], v: Validate[T, P]) {

  type RefinedType = F[T, P]

  def refine(t: T): Either[String, RefinedType] =
    rt.refine[P](t)

  def refineM: RefineMFullyApplied[F, T, P] =
    rt.refineMF[T, P]
}

// usage:
/*
type Natural = Long Refined NonNegative
object Natural extends RefineOps[Natural]

*/
trait RefineOps1[FTP] {

  //def dealias: Unit = macro macro_impl

  def macro_impl(c: scala.reflect.macros.blackbox.Context): c.Expr[Unit] = {
    import c.universe._
    reify(())
  }

  //def refine(t: dealias.T): Either[String, dealias.F[dealias.T, dealias.P]] =
  //  dealias.refType.refine[dealias.P](t)

  /*
  def refineM: RefineMFullyApplied[F, T, P] =
    rt.refineMF[T, P]
  */
}

class MkRefineOps[u] extends StaticAnnotation {

}

@MkRefineOps[Int] trait RefineOps3[FTP]

class RefineOps2[FTP](implicit val d: Dealias[FTP]) {

  def refine(t: d.T): Either[String, d.F[d.T, d.P]] =
    d.refType.refine[d.P][d.T](t)(d.validate)

  //def refineM: RefineMFullyApplied[d.F, d.T, d.P] =
  //  d.refType.refineMF[d.T, d.P]
}

trait Dealias[FTP] {
  type F[_, _]
  type T
  type P

  def refType: RefType[F]
  def validate: Validate[T, P]
}

object Dealias {

  def apply[FTP](implicit d: Dealias[FTP]): Dealias.Aux[FTP, d.F, d.T, d.P] = d

  type Aux[FTP, F0[_, _], T0, P0] = Dealias[FTP] {
    type F[x, y] = F0[x, y]
    type T = T0
    type P = P0
  }

  implicit def instance[F0[_, _], T0, P0](implicit r: RefType[F0], v: Validate[T0, P0]): Dealias.Aux[F0[T0, P0], F0, T0, P0] =
    new Dealias[F0[T0, P0]] {
      override type F[x, y] = F0[x, y]
      override type P = P0
      override type T = T0
      override def refType: RefType[F0] = r
      override def validate: Validate[T0, P0] = v
    }
}
