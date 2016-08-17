package eu.timepit.refined.scalacheck

import eu.timepit.refined.api.RefType
import eu.timepit.refined.collection.Forall
import org.scalacheck.{ Arbitrary, Gen }
import org.scalacheck.util.Buildable

object collection extends CollectionViewInstances {

  implicit def forallArbitrary[F[_, _], A, P, T[a] <: Traversable[a]](
    implicit
    rt: RefType[F],
    arbP: Arbitrary[F[A, P]],
    bt: Buildable[A, T[A]]
  ): Arbitrary[F[T[A], Forall[P]]] =
    arbitraryRefType(Gen.buildableOf(arbP.arbitrary.map(rt.unwrap)))
}

private[refined] trait CollectionViewInstances {

  implicit def forallArbitraryView[F[_, _], A, P, T](
    implicit
    rt: RefType[F],
    arbP: Arbitrary[F[A, P]],
    bt: Buildable[A, T],
    ev: T => Traversable[A]
  ): Arbitrary[F[T, Forall[P]]] =
    arbitraryRefType(Gen.buildableOf(arbP.arbitrary.map(rt.unwrap)))
}
