package eu.timepit.refined.slick

import eu.timepit.refined.api.{ RefType, Validate }
import slick.driver.H2Driver.api._

import scala.reflect.ClassTag

package object h2 {

  implicit def refTypeColumnType[F[_, _], T, P](
    implicit
    columnType: ColumnType[T],
    refType: RefType[F],
    validate: Validate[T, P],
    classTag: ClassTag[F[T, P]]
  ): ColumnType[F[T, P]] =
    MappedColumnType.base(
      refType.unwrap,
      (t: T) => refType.refine[P](t).right.getOrElse(???)
    )
}
