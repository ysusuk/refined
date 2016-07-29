# Dependent refinement

```tut:silent
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Greater
import eu.timepit.refined.string.StartsWith
```

Scala's path dependent types makes it possible to express refinements
that depend other statically known values:

```tut
def foo(a: String)(b: String Refined StartsWith[a.type]) = a + b
```

```tut
foo("ab")("abcd")
```

```tut:fail
foo("cd")("abcd")
```

```tut
def bar(i: Int)(j: Int Refined Greater[i.type]) = j - i
```

```tut
bar(2)(4)
```

```tut:fail
bar(6)(4)
```
