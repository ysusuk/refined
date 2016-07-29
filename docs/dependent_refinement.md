# Dependent refinement

```scala
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Greater
import eu.timepit.refined.string.StartsWith
```

Scala's path dependent types makes it possible to express refinements
that depend other statically known values:

```scala
scala> def foo(a: String)(b: String Refined StartsWith[a.type]) = a + b
foo: (a: String)(b: eu.timepit.refined.api.Refined[String,eu.timepit.refined.string.StartsWith[a.type]])String
```

```scala
scala> foo("ab")("abcd")
res0: String = ababcd
```

```scala
scala> foo("cd")("abcd")
<console>:20: error: Predicate failed: "abcd".startsWith("cd").
       foo("cd")("abcd")
                 ^
```

```scala
scala> def bar(i: Int)(j: Int Refined Greater[i.type]) = j - i
bar: (i: Int)(j: eu.timepit.refined.api.Refined[Int,eu.timepit.refined.numeric.Greater[i.type]])Int
```

```scala
scala> bar(2)(4)
res2: Int = 2
```

```scala
scala> bar(6)(4)
<console>:20: error: Predicate failed: (4 > 6).
       bar(6)(4)
              ^
```
