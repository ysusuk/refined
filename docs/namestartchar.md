# A ridiculously long refinement

This example demonstrates how [`NameStartChar`](http://www.w3.org/TR/xml11/#NT-NameStartChar)
as defined in the XML specification can be written as a refined type.

The formal definition of `NameStartChar` looks like this:
```
NameStartChar ::=
    ":"
  | [A-Z]
  | "_"
  | [a-z]
  | [#xC0-#xD6]
  | [#xD8-#xF6]
  | [#xF8-#x2FF]
  | [#x370-#x37D]
  | [#x37F-#x1FFF]
  | [#x200C-#x200D]
  | [#x2070-#x218F]
  | [#x2C00-#x2FEF]
  | [#x3001-#xD7FF]
  | [#xF900-#xFDCF]
  | [#xFDF0-#xFFFD]
  | [#x10000-#xEFFFF]
```

We can translate that to a refined type by using the `AnyOf`, `Equal`,
and `Interval.Closed` predicates:
```scala
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.AnyOf
import eu.timepit.refined.generic.Equal
import eu.timepit.refined.numeric.Interval
import shapeless.{ ::, HNil }

type NameStartChar = Char Refined AnyOf[
     Equal[':']
  :: Interval.Closed['A', 'Z']
  :: Equal['_']
  :: Interval.Closed['a', 'z']
  :: Interval.Closed['\u00C0', '\u00D6']
  :: Interval.Closed['\u00D8', '\u00F6']
  :: Interval.Closed['\u00F8', '\u02FF']
  :: Interval.Closed['\u0370', '\u037D']
  :: Interval.Closed['\u200C', '\u200D']
  :: Interval.Closed['\u2070', '\u218F']
  :: Interval.Closed['\u2C00', '\u2FEF']
  :: Interval.Closed['\u3001', '\uD7FF']
  :: Interval.Closed['\uF900', '\uFDCF']
  :: Interval.Closed['\uFDF0', '\uFFFD']
  :: HNil]
```

And then use it. `'Ä'` is a valid `NameStartChar`:
```scala
scala> val a: NameStartChar = 'Ä'
a: NameStartChar = Ä
```

But `';'` is not:
```scala
scala> val b: NameStartChar = ';'
<console>:22: error: Predicate failed: ((; == :) || (!(; < A) && !(; > Z)) || (; == _) || (!(; < a) && !(; > z)) || (!(; < À) && !(; > Ö)) || (!(; < Ø) && !(; > ö)) || (!(; < ø) && !(; > ˿)) || (!(; < Ͱ) && !(; > ͽ)) || (!(; < ‌) && !(; > ‍)) || (!(; < ⁰) && !(; > ↏)) || (!(; < Ⰰ) && !(; > ⿯)) || (!(; < 、) && !(; > ퟿)) || (!(; < 豈) && !(; > ﷏)) || (!(; < ﷰ) && !(; > �)) || false).
       val b: NameStartChar = ';'
                              ^
```
