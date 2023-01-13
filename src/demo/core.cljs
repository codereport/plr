(ns demo.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clojure.string :as str]
   [stylefy.core :as stylefy]
   ))

(defonce state (r/atom {:top-padding "250px"
                        :results-table [:tr]}))

(defonce debug (r/atom {:info ""}))

(def by-key-map
  {

"APL@, (catenate)" {:lang "APL" :algo ", (catenate)" :lib "-" :id 21 :doc "http://microapl.com/apl_help/ch_020_020_490.htm" :sig "-"}
"APL@. (inner product)" {:lang "APL" :algo ". (inner product)" :lib "-" :id 7 :doc "http://microapl.com/apl_help/ch_020_020_880.htm" :sig "-"}
"Rust@.. / Range" {:lang "Rust" :algo ".. / Range" :lib "ops" :id 8 :doc "https://doc.rust-lang.org/std/ops/struct.Range.html" :sig "-"}
"APL@/ (compress)" {:lang "APL" :algo "/ (compress)" :lib "-" :id 3 :doc "http://microapl.com/apl_help/ch_020_020_840.htm" :sig "-"}
"J@/ (insert)" {:lang "J" :algo "/ (insert)" :lib "-" :id 1 :doc "https://code.jsoftware.com/wiki/Vocabulary/slash" :sig "-"}
"APL@/ (n-wise reduce)" {:lang "APL" :algo "/ (n-wise reduce)" :lib "-" :id 5 :doc "http://microapl.com/apl_help/ch_020_020_800.htm" :sig "-"}
"APL@/ (reduce)" {:lang "APL" :algo "/ (reduce)" :lib "-" :id 1 :doc "http://microapl.com/apl_help/ch_020_020_800.htm" :sig "-"}
"J@\": (default format)" {:lang "J" :algo "\": (default format)" :lib "-" :id 16 :doc "https://code.jsoftware.com/wiki/Vocabulary/quoteco" :sig "-"}
"J@\\ (prefix)" {:lang "J" :algo "\\ (prefix)" :lib "-" :id 4 :doc "https://code.jsoftware.com/wiki/Vocabulary/bslash" :sig "-"}
"APL@\\ (scan)" {:lang "APL" :algo "\\ (scan)" :lib "-" :id 4 :doc "http://microapl.com/apl_help/ch_020_020_820.htm" :sig "-"}
"APL@¨ (each)" {:lang "APL" :algo "¨ (each)" :lib "-" :id 2 :doc "http://microapl.com/apl_help/ch_020_020_900.htm" :sig "-"}
"APL@∘. (outer product)" {:lang "APL" :algo "∘. (outer product)" :lib "-" :id 6 :doc "http://microapl.com/apl_help/ch_020_020_890.htm" :sig "-"}
"APL@⊆ (partition)" {:lang "APL" :algo "⊆ (partition)" :lib "-" :id 23 :doc "http://microapl.com/apl_help/ch_020_020_590.htm" :sig "-"}
"APL@⍕ (format)" {:lang "APL" :algo "⍕ (format)" :lib "-" :id 16 :doc "http://microapl.com/apl_help/ch_020_020_680.htm" :sig "-"}
"APL@⍳ (iota)" {:lang "APL" :algo "⍳ (iota)" :lib "-" :id 8 :doc "http://microapl.com/apl_help/ch_020_020_150.htm" :sig "-"}
"APL@⍸ (where)" {:lang "APL" :algo "⍸ (where)" :lib "-" :id 18 :doc "https://help.dyalog.com/17.1/#Language/Primitive%20Functions/Where.htm#kanchor3608" :sig "-"}
"C++@accumulate" {:lang "C++" :algo "accumulate" :lib "<numeric>" :id 1 :doc "https://en.cppreference.com/w/cpp/algorithm/accumulate" :sig "-"}
"Python@accumulate" {:lang "Python" :algo "accumulate" :lib "itertools" :id 4 :doc "https://docs.python.org/3/library/itertools.html#itertools.accumulate" :sig "-"}
"C++@adjacent_difference" {:lang "C++" :algo "adjacent_difference" :lib "<numeric>" :id 5 :doc "https://en.cppreference.com/w/cpp/algorithm/adjacent_difference" :sig "-"}
"CUDA@adjacent_difference" {:lang "CUDA" :algo "adjacent_difference" :lib "Thrust" :id 5 :doc "https://thrust.github.io/doc/group__transformations_gaa41d309b53fa03bf13fe35a184148400.html" :sig "-"}
"C#@Aggregate" {:lang "C#" :algo "Aggregate" :lib "Enumerable" :id 1 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.linq.enumerable.aggregate" :sig "-"}
"D@any" {:lang "D" :algo "any" :lib "algorithm.searching" :id 20 :doc "https://dlang.org/library/std/algorithm/searching/any.html" :sig "-"}
"Haskell@any" {:lang "Haskell" :algo "any" :lib "Prelude" :id 20 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:any" :sig "(a -> Bool) -> [a] -> Bool"}
"Kotlin@any" {:lang "Kotlin" :algo "any" :lib "collections" :id 20 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/any.html" :sig "-"}
"Python@any" {:lang "Python" :algo "any" :lib "builtin" :id 20 :doc "https://docs.python.org/3/library/functions.html#any" :sig "-"}
"Rust@any" {:lang "Rust" :algo "any" :lib "trait.Iterator" :id 20 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.any" :sig "-"}
"Elixir@any?" {:lang "Elixir" :algo "any?" :lib "Enum" :id 20 :doc "https://hexdocs.pm/elixir/Enum.html#any?/2" :sig "-"}
"Ruby@any?" {:lang "Ruby" :algo "any?" :lib "Enumerable" :id 20 :doc "https://apidock.com/ruby/Enumerable/any%3F" :sig "-"}
"C++@any_of" {:lang "C++" :algo "any_of" :lib "<algorithm>" :id 20 :doc "https://en.cppreference.com/w/cpp/algorithm/all_any_none_of" :sig "-"}
"D@canFind" {:lang "D" :algo "canFind" :lib "algorithm.searching" :id 17 :doc "https://dlang.org/library/std/algorithm/searching/can_find.can_find.html" :sig "-"}
"Haskell@concat" {:lang "Haskell" :algo "concat" :lib "Prelude" :id 21 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:concat" :sig "[[a]] -> [a]"}
;; "Clojure@concat" {:lang "Clojure" :algo "concat" :lib "core" :id 21 :doc "https://clojuredocs.org/clojure.core/concat" :sig "-"}
;; "Elixir@concat" {:lang "Elixir" :algo "concat" :lib "Enum" :id 21 :doc "https://hexdocs.pm/elixir/Enum.html#concat/1" :sig "-"}
"Kotlin@contains" {:lang "Kotlin" :algo "contains" :lib "collections" :id 17 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/contains.html" :sig "-"}
"Scala@contains" {:lang "Scala" :algo "contains" :lib "various" :id 17 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#contains[A1%3E:A](elem:A1):Boolean" :sig "-"}
"Clojure@contains?" {:lang "Clojure" :algo "contains?" :lib "core" :id 17 :doc "https://clojuredocs.org/clojure.core/contains_q" :sig "-"}
"Haskell@count" {:lang "Haskell" :algo "count" :lib "Data.List.Unique" :id 19 :doc "https://hackage.haskell.org/package/Unique-0.4.7.7/docs/Data-List-Unique.html#v:count" :sig "[a] -> [(a,Int)]"}
"Python@Counter*" {:lang "Python" :algo "Counter*" :lib "collections" :id 19 :doc "https://docs.python.org/2/library/collections.html#collections.Counter" :sig "-"}
"D@cumulativeFold" {:lang "D" :algo "cumulativeFold" :lib "algorithm.iteration" :id 4 :doc "https://dlang.org/library/std/algorithm/iteration/cumulative_fold.html" :sig "-"}
"C#@Distinct" {:lang "C#" :algo "Distinct" :lib "Enumerable" :id 15 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.linq.enumerable.distinct" :sig "-"}
"Clojure@distinct" {:lang "Clojure" :algo "distinct" :lib "core" :id 15 :doc "https://clojuredocs.org/clojure.core/distinct" :sig "-"}
"F#@Distinct" {:lang "F#" :algo "Distinct" :lib "Seq" :id 15 :doc "https://www.dotnetperls.com/duplicates-fs" :sig "-"}
"Kotlin@distinct" {:lang "Kotlin" :algo "distinct" :lib "collections" :id 15 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/distinct.html" :sig "-"}
"Scala@distinct" {:lang "Scala" :algo "distinct" :lib "various" :id 15 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#distinct:Repr" :sig "-"}
"J@each" {:lang "J" :algo "each" :lib "-" :id 2 :doc "https://www.jsoftware.com/help/primer/each.htm" :sig "-"}
"q@each" {:lang "q" :algo "each" :lib "-" :id 2 :doc "https://code.kx.com/q/ref/each/" :sig "-"}
"Haskell@elem" {:lang "Haskell" :algo "elem" :lib "Prelude" :id 17 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:elem" :sig "a -> [a] -> Bool"}
"D@enumerate" {:lang "D" :algo "enumerate" :lib "range" :id 9 :doc "https://dlang.org/library/std/range/enumerate.html" :sig "-"}
"Python@enumerate" {:lang "Python" :algo "enumerate" :lib "-" :id 9 :doc "http://book.pythontips.com/en/latest/enumerate.html" :sig "-"}
"Racket@enumerate" {:lang "Racket" :algo "enumerate" :lib "list-utils" :id 9 :doc "https://docs.racket-lang.org/list-utils/index.html?q=freq#%28def._%28%28lib._list-utils%2Fmain..rkt%29._enumerate%29%29" :sig "-"}
"Rust@enumerate" {:lang "Rust" :algo "enumerate" :lib "trait.Iterator" :id 9 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.enumerate" :sig "-"}
"F#@exists" {:lang "F#" :algo "exists" :lib "List" :id 20 :doc "https://docs.microsoft.com/en-us/dotnet/fsharp/language-reference/lists" :sig "-"}
"Scala@exists" {:lang "Scala" :algo "exists" :lib "various" :id 20 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html" :sig "-"}
"Haskell@filter" {:lang "Haskell" :algo "filter" :lib "Data.List" :id 3 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:filter" :sig "(a -> Bool) -> [a] -> [a]"}
"Rust@fold" {:lang "Rust" :algo "fold" :lib "trait.Iterator" :id 1 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.fold" :sig "-"}
"Haskell@foldl" {:lang "Haskell" :algo "foldl" :lib "Data.List" :id 1 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:foldl" :sig "(b -> a -> b) -> b -> [a] -> b"}
"Racket@foldl" {:lang "Racket" :algo "foldl" :lib "base" :id 1 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket%2Fprivate%2Flist..rkt%29._foldl%29%29" :sig "-"}
"Clojure@frequencies" {:lang "Clojure" :algo "frequencies" :lib "core" :id 19 :doc "https://clojuredocs.org/clojure.core/frequencies" :sig "-"}
"Racket@frequencies" {:lang "Racket" :algo "frequencies" :lib "list-utils" :id 19 :doc "https://docs.racket-lang.org/list-utils/index.html?q=freq#%28def._%28%28lib._list-utils%2Fmain..rkt%29._frequencies%29%29" :sig "-"}
"J@i. (integers)" {:lang "J" :algo "i. (integers)" :lib "-" :id 8 :doc "https://code.jsoftware.com/wiki/Vocabulary/idot" :sig "-"}
"Python@in" {:lang "Python" :algo "in" :lib "keyword" :id 17 :doc "https://www.programiz.com/python-programming/keyword-list#in" :sig "-"}
"Ruby@include?" {:lang "Ruby" :algo "include?" :lib "various" :id 17 :doc "https://apidock.com/ruby/Array/include%3F" :sig "-"}
"C++@inclusive_scan" {:lang "C++" :algo "inclusive_scan" :lib "<numeric>" :id 4 :doc "https://en.cppreference.com/w/cpp/algorithm/inclusive_scan" :sig "-"}
"CUDA@inclusive_scan" {:lang "CUDA" :algo "inclusive_scan" :lib "Thrust" :id 4 :doc "https://thrust.github.io/doc/group__prefixsums_gafb24ad76101263038b0acaddc094d70a.html" :sig "-"}
"Haskell@indexed" {:lang "Haskell" :algo "indexed" :lib "Data.List.Index" :id 9 :doc "https://hackage.haskell.org/package/ilist-0.4.0.0/docs/Data-List-Index.html#v:indexed" :sig "[a] -> [(Int,a)]"}
"Racket@indexed" {:lang "Racket" :algo "indexed" :lib "data-collections" :id 9 :doc "https://docs.racket-lang.org/collections/collections-api.html?q=chunk-while#%28def._%28%28lib._data%2Fcollection..rkt%29._indexed%29%29" :sig "-"}
"C++@inner_product" {:lang "C++" :algo "inner_product" :lib "<numeric>" :id 7 :doc "https://en.cppreference.com/w/cpp/algorithm/inner_product" :sig "-"}
"C++@iota" {:lang "C++" :algo "iota" :lib "<numeric>" :id 8 :doc "https://en.cppreference.com/w/cpp/algorithm/iota" :sig "-"}
"D@iota" {:lang "D" :algo "iota" :lib "range" :id 8 :doc "https://dlang.org/library/std/range/iota.html" :sig "-"}
"Go@iota" {:lang "Go" :algo "iota" :lib "-" :id 8 :doc "https://github.com/golang/go/wiki/Iota" :sig "-"}
"Racket@join" {:lang "Racket" :algo "join" :lib "base" :id 21 :doc "https://docs.racket-lang.org/algebraic/class_base.html?q=some#%28def._%28%28lib._algebraic%2Fcontrol%2Fmonad..rkt%29._join%29%29" :sig "-"}
"Ruby@join" {:lang "Ruby" :algo "join" :lib "various" :id 21 :doc "https://apidock.com/ruby/Array/join" :sig "-"}
"Clojure@join" {:lang "Clojure" :algo "join" :lib "string" :id 21 :doc "https://clojuredocs.org/clojure.string/join" :sig "-"}
"D@join" {:lang "D" :algo "join" :lib "array" :id 21 :doc "https://dlang.org/library/std/array/join.html" :sig "-"}
"Rust@join" {:lang "Rust" :algo "join" :lib "Vec" :id 21 :doc "https://doc.rust-lang.org/std/vec/struct.Vec.html#method.join" :sig "-"}
"Go@join" {:lang "Go" :algo "join" :lib "strings" :id 21 :doc "https://golang.org/pkg/strings/#Join" :sig "-"}
"C#@join" {:lang "C#" :algo "join" :lib "String" :id 21 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.string.join?view=netframework-4.8" :sig "-"}
"F#@join" {:lang "F#" :algo "join" :lib "String" :id 21 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.string.join?view=netframework-4.8" :sig "-"}
"Python@join*" {:lang "Python" :algo "join*" :lib "String" :id 21 :doc "https://www.w3schools.com/python/ref_string_join.asp" :sig "-"}
"Kotlin@joinTo" {:lang "Kotlin" :algo "joinTo" :lib "collections" :id 21 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/join-to.html" :sig "-"}
"Clojure@map" {:lang "Clojure" :algo "map" :lib "core" :id 2 :doc "https://clojuredocs.org/clojure.core/map" :sig "-"}
"D@map" {:lang "D" :algo "map" :lib "algorithm.iteration" :id 2 :doc "https://dlang.org/library/std/algorithm/iteration/map.html" :sig "-"}
"Elixir@map" {:lang "Elixir" :algo "map" :lib "Enum" :id 2 :doc "https://hexdocs.pm/elixir/Enum.html#map/2" :sig "-"}
"Haskell@map" {:lang "Haskell" :algo "map" :lib "Data.List" :id 2 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:map" :sig ""}
"Kotlin@map" {:lang "Kotlin" :algo "map" :lib "collections" :id 2 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/map.html" :sig "-"}
"Python@map" {:lang "Python" :algo "map" :lib "builtin" :id 2 :doc "https://docs.python.org/3/library/functions.html#map" :sig "-"}
"Racket@map" {:lang "Racket" :algo "map" :lib "base" :id 2 :doc "https://docs.racket-lang.org/reference/pairs.html?q=map#%28def._%28%28lib._racket%2Fprivate%2Fmap..rkt%29._map%29%29" :sig "-"}
"Ruby@map" {:lang "Ruby" :algo "map" :lib "Enumerable" :id 2 :doc "https://apidock.com/ruby/Enumerable/map" :sig "-"}
"Rust@map" {:lang "Rust" :algo "map" :lib "trait.Iterator" :id 2 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.map" :sig "-"}
"Scala@map" {:lang "Scala" :algo "map" :lib "various" :id 2 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#map[B](f:A=%3EB):scala.collection.immutable.Vector[B]" :sig "-"}
"Haskell@mapAdjacent" {:lang "Haskell" :algo "mapAdjacent" :lib "Data.List.HT" :id 5 :doc "https://hackage.haskell.org/package/utility-ht-0.0.15/docs/Data-List-HT.html#v:mapAdjacent" :sig "(a -> a -> b) -> [a] -> [b]"}
"Clojure@map-indexed*" {:lang "Clojure" :algo "map-indexed*" :lib "core" :id 9 :doc "https://clojuredocs.org/clojure.core/map-indexed" :sig "-"}
"Racket@member*" {:lang "Racket" :algo "member*" :lib "base" :id 17 :doc "https://docs.racket-lang.org/reference/pairs.html?q=map#%28def._%28%28lib._racket%2Fprivate%2Fbase..rkt%29._member%29%29" :sig "-"}
"Elixir@member?" {:lang "Elixir" :algo "member?" :lib "Enum" :id 17 :doc "https://hexdocs.pm/elixir/Enum.html#member?/2" :sig "-"}
"Racket@memf*" {:lang "Racket" :algo "memf*" :lib "base" :id 20 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket%2Fprivate%2Flist..rkt%29._memf%29%29" :sig "-"}
"Haskell@outerProduct" {:lang "Haskell" :algo "outerProduct" :lib "Data.List.HT" :id 6 :doc "https://hackage.haskell.org/package/utility-ht-0.0.15/docs/Data-List-HT.html#v:outerProduct" :sig "(a -> b -> c) -> [a] -> [b] -> [[a]]"}
"Clojure@outer-product" {:lang "Clojure" :algo "outer-product" :lib "core.matrix" :id 6 :doc "https://mikera.github.io/core.matrix/doc/clojure.core.matrix.html#var-outer-product" :sig "-"}
"q@over" {:lang "q" :algo "over" :lib "-" :id 1 :doc "https://code.kx.com/q/ref/over/" :sig "-"}
"C++@partial_sum" {:lang "C++" :algo "partial_sum" :lib "<numeric>" :id 4 :doc "https://en.cppreference.com/w/cpp/algorithm/partial_sum" :sig "-"}
"C++@partition" {:lang "C++" :algo "partition" :lib "<algorithm>" :id 13 :doc "https://en.cppreference.com/w/cpp/algorithm/partition" :sig "-"}
"Clojure@partition" {:lang "Clojure" :algo "partition" :lib "core" :id 11 :doc "https://clojuredocs.org/clojure.core/partition" :sig "-"}
"CUDA@partition" {:lang "CUDA" :algo "partition" :lib "Thrust" :id 13 :doc "https://thrust.github.io/doc/group__partitioning_gac5cdbb402c5473ca92e95bc73ecaf13c.html" :sig "-"}
"D@partition" {:lang "D" :algo "partition" :lib "algorithm.sorting" :id 13 :doc "https://dlang.org/library/std/algorithm/sorting/partition.html" :sig "-"}
"Haskell@partition" {:lang "Haskell" :algo "partition" :lib "Data.List" :id 10 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Data-List.html#v:partition" :sig "(a -> Bool) -> [a] -> ([a],[a])"}
"Kotlin@partition" {:lang "Kotlin" :algo "partition" :lib "collections" :id 10 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/partition.html" :sig "-"}
"Racket@partition" {:lang "Racket" :algo "partition" :lib "base" :id 10 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket%2Flist..rkt%29._partition%29%29" :sig "-"}
"Ruby@partition" {:lang "Ruby" :algo "partition" :lib "Enumerable" :id 10 :doc "https://apidock.com/ruby/Enumerable/partition" :sig "-"}
"Rust@partition" {:lang "Rust" :algo "partition" :lib "trait.Iterator" :id 10 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.partition" :sig "-"}
"C++@partition_copy" {:lang "C++" :algo "partition_copy" :lib "<algorithm>" :id 10 :doc "https://en.cppreference.com/w/cpp/algorithm/partition_copy" :sig "-"}
"q@prior" {:lang "q" :algo "prior" :lib "-" :id 5 :doc "https://code.kx.com/q/ref/prior/" :sig "-"}
"Clojure@range" {:lang "Clojure" :algo "range" :lib "core" :id 8 :doc "https://clojuredocs.org/clojure.core/range" :sig "-"}
"Haskell@range" {:lang "Haskell" :algo "range" :lib "Data.List.HT" :id 8 :doc "https://hackage.haskell.org/package/utility-ht-0.0.15/docs/Data-List-HT.html#v:range" :sig "Int -> [Int]"}
"Python@range" {:lang "Python" :algo "range" :lib "-" :id 8 :doc "https://docs.python.org/3/library/functions.html#func-range" :sig "-"}
"Racket@range" {:lang "Racket" :algo "range" :lib "base" :id 8 :doc "https://docs.racket-lang.org/reference/pairs.html?q=enumerate#%28def._%28%28lib._racket%2Flist..rkt%29._range%29%29" :sig "-"}
"C++@reduce" {:lang "C++" :algo "reduce" :lib "<numeric>" :id 1 :doc "https://en.cppreference.com/w/cpp/algorithm/reduce" :sig "-"}
"Clojure@reduce" {:lang "Clojure" :algo "reduce" :lib "core" :id 1 :doc "https://clojuredocs.org/clojure.core/reduce" :sig "-"}
"CUDA@reduce" {:lang "CUDA" :algo "reduce" :lib "Thrust" :id 1 :doc "https://thrust.github.io/doc/group__reductions_ga43eea9a000f912716189687306884fc7.html" :sig "-"}
"D@reduce" {:lang "D" :algo "reduce" :lib "algorithm.iteration" :id 1 :doc "https://dlang.org/library/std/algorithm/iteration/reduce.html" :sig "-"}
"Elixir@reduce" {:lang "Elixir" :algo "reduce" :lib "Enum" :id 1 :doc "https://hexdocs.pm/elixir/Enum.html#reduce/2" :sig "-"}
"Kotlin@reduce" {:lang "Kotlin" :algo "reduce" :lib "collections" :id 1 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/reduce.html" :sig "-"}
"Python@reduce" {:lang "Python" :algo "reduce" :lib "itertools" :id 1 :doc "https://book.pythontips.com/en/latest/map_filter.html#reduce" :sig "-"}
"Ruby@reduce" {:lang "Ruby" :algo "reduce" :lib "Enumerable" :id 1 :doc "https://apidock.com/ruby/Enumerable/reduce" :sig "-"}
"Clojure@reductions" {:lang "Clojure" :algo "reductions" :lib "core" :id 4 :doc "https://clojuredocs.org/clojure.core/reductions" :sig "-"}
"q@scan" {:lang "q" :algo "scan" :lib "-" :id 4 :doc "https://code.kx.com/q/ref/over/" :sig "-"}
"Rust@scan" {:lang "Rust" :algo "scan" :lib "trait.Iterator" :id 4 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.scan" :sig "-"}
"Scala@scan" {:lang "Scala" :algo "scan" :lib "various" :id 4 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#scan[B%3E:A,That](z:B)(op:(B,B)=%3EB)(implicitcbf:scala.collection.generic.CanBuildFrom[Repr,B,That]):That" :sig "-"}
"Kotlin@runningReduce" {:lang "Kotlin" :algo "runningReduce" :lib "collections" :id 4 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/running-reduce.html" :sig "-"}
"F#@scan" {:lang "F#" :algo "scan" :lib "Seq" :id 4 :doc "https://docs.microsoft.com/en-us/dotnet/fsharp/language-reference/sequences" :sig "-"}
"Elixir@scan" {:lang "Elixir" :algo "scan" :lib "Enum" :id 4 :doc "https://hexdocs.pm/elixir/Enum.html#scan/2" :sig "-"}
"Haskell@scanl1" {:lang "Haskell" :algo "scanl1" :lib "Data.List" :id 4 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:scanl1" :sig ""}
"C#@Select" {:lang "C#" :algo "Select" :lib "Enumerable" :id 9 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.linq.enumerable.select" :sig "-"}
"CUDA@sequence" {:lang "CUDA" :algo "sequence" :lib "Thrust" :id 8 :doc "https://thrust.github.io/doc/group__transformations_ga233a3db0c5031023c8e9385acd4b9759.html" :sig "-"}
"Haskell@show" {:lang "Haskell" :algo "show" :lib "Prelude" :id 16 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Prelude.html#v:show" :sig "a -> String"}
"Clojure@some" {:lang "Clojure" :algo "some" :lib "core" :id 20 :doc "https://clojuredocs.org/clojure.core/some" :sig "-"}
"Haskell@sortUniq" {:lang "Haskell" :algo "sortUniq" :lib "Data.List.Unique" :id 15 :doc "https://hackage.haskell.org/package/Unique-0.4.7.7/docs/Data-List-Unique.html#v:sortUniq" :sig "[a] -> [a]"}
"Elixir@split_with" {:lang "Elixir" :algo "split_with" :lib "Enum" :id 10 :doc "https://hexdocs.pm/elixir/Enum.html#split_with/2" :sig "-"}
"Clojure@str" {:lang "Clojure" :algo "str" :lib "core" :id 16 :doc "https://clojuredocs.org/clojure.core/str" :sig "-"}
"Python@str" {:lang "Python" :algo "str" :lib "-" :id 16 :doc "https://docs.python.org/3/library/stdtypes.html#text-sequence-type-str" :sig "-"}
"q@string" {:lang "q" :algo "string" :lib "-" :id 16 :doc "https://code.kx.com/v2/ref/string/" :sig "-"}
"q@til" {:lang "q" :algo "til" :lib "-" :id 8 :doc "https://code.kx.com/q/ref/til/" :sig "-"}
"C++@to_string" {:lang "C++" :algo "to_string" :lib "<string>" :id 16 :doc "https://en.cppreference.com/w/cpp/string/basic_string/to_string" :sig "-"}
"Elixir@to_string" {:lang "Elixir" :algo "to_string" :lib "various" :id 16 :doc "https://hexdocs.pm/elixir/Integer.html#to_string/1" :sig "-"}
"Rust@to_string" {:lang "Rust" :algo "to_string" :lib "string" :id 16 :doc "https://doc.rust-lang.org/std/string/trait.ToString.html" :sig "-"}
"Scala@toString" {:lang "Scala" :algo "toString" :lib "various" :id 16 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#toString():String" :sig "-"}
"C++@transform" {:lang "C++" :algo "transform" :lib "<algorithm>" :id 2 :doc "https://en.cppreference.com/w/cpp/algorithm/transform" :sig "-"}
"BASH@uniq" {:lang "BASH" :algo "uniq" :lib "-" :id 14 :doc "https://ss64.com/bash/uniq.html" :sig "-"}
"D@uniq" {:lang "D" :algo "uniq" :lib "algorithm.iteration" :id 14 :doc "https://dlang.org/phobos/std_algorithm_iteration.html#uniq" :sig "-"}
"Elixir@uniq" {:lang "Elixir" :algo "uniq" :lib "Enum" :id 15 :doc "https://hexdocs.pm/elixir/Enum.html#uniq/1" :sig "-"}
"Ruby@uniq" {:lang "Ruby" :algo "uniq" :lib "Array" :id 15 :doc "https://apidock.com/ruby/Array/uniq" :sig "-"}
"C++@unique" {:lang "C++" :algo "unique" :lib "<algorithm>" :id 14 :doc "https://en.cppreference.com/w/cpp/algorithm/unique" :sig "-"}
"Rust@unique" {:lang "Rust" :algo "unique" :lib "itertools" :id 15 :doc "https://docs.rs/itertools/0.7.6/itertools/structs/struct.Unique.html" :sig "-"}
"Scala@until" {:lang "Scala" :algo "until" :lib "Range" :id 8 :doc "https://www.scala-lang.org/api/2.12.3/scala/collection/immutable/Range.html" :sig "-"}
;;"q@where" {:lang "q" :algo "where" :lib "-" :id 18 :doc "https://code.kx.com/q4m3/A_Built-in_Functions/#a107-where" :sig "-"}
"Elixir@with_index" {:lang "Elixir" :algo "with_index" :lib "Enum" :id 9 :doc "https://hexdocs.pm/elixir/Enum.html#with_index/2" :sig "-"}
"Ruby@with_index" {:lang "Ruby" :algo "with_index" :lib "Enumerable" :id 9 :doc "https://apidock.com/ruby/Enumerator/with_index" :sig "-"}
"Kotlin@withIndex" {:lang "Kotlin" :algo "withIndex" :lib "collections" :id 9 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/with-index.html" :sig "-"}
"Scala@zipWithIndex" {:lang "Scala" :algo "zipWithIndex" :lib "various" :id 9 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#zipWithIndex:scala.collection.immutable.Vector[(A,Int)]" :sig "-"}
"Kotlin@zipWithNext" {:lang "Kotlin" :algo "zipWithNext" :lib "collections" :id 5 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/zip-with-next.html" :sig "-"}
;; "RAPIDS (C++)@scan" {:lang "RAPIDS (C++)" :algo "scan" :lib "Series" :id 4 :doc "https://docs.rapids.ai/api/libcudf/stable/namespacecudf_1_1experimental.html#a3fb513cb9b4c26c11309b8b7c957724a" :sig "-"}
;; "RAPIDS (Python)@cumsum" {:lang "RAPIDS (Python)" :algo "cumsum" :lib "Series" :id 4 :doc "https://docs.rapids.ai/api/cudf/nightly/api.html#cudf.core.series.Series.cumsum" :sig "-"}
;; "pandas@cumsum" {:lang "pandas" :algo "cumsum" :lib "Series" :id 4 :doc "https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.Series.cumsum.html?highlight=cumsum#pandas.Series.cumsum" :sig "-"}
"Python@zip(*)" {:lang "Python" :algo "zip(*)" :lib "-" :id 22 :doc "https://www.geeksforgeeks.org/transpose-matrix-single-line-python/" :sig "-"}
"pandas@transpose" {:lang "pandas" :algo "transpose" :lib "DataFrame" :id 22 :doc "https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.DataFrame.transpose.html" :sig "-"}
"Ruby@transpose" {:lang "Ruby" :algo "transpose" :lib "Array" :id 22 :doc "https://apidock.com/ruby/Array/transpose" :sig "-"}
"APL@⍉ (transpose)" {:lang "APL" :algo "⍉ (transpose)" :lib "-" :id 22 :doc "http://microapl.com/apl_help/ch_020_020_540.htm" :sig "-"}
"Haskell@transpose" {:lang "Haskell" :algo "transpose" :lib "Data.List" :id 22 :doc "https://hackage.haskell.org/package/base-4.12.0.0/docs/Data-List.html#v:transpose" :sig "-"}
"q@flip" {:lang "q" :algo "flip" :lib "- " :id 22 :doc "https://code.kx.com/v2/ref/flip/" :sig "-"}
"J@|: (transpose)" {:lang "J" :algo "|: (transpose)" :lib "- " :id 22 :doc "https://code.jsoftware.com/wiki/Vocabulary/barco" :sig "-"}
"Clojure@transpose" {:lang "Clojure" :algo "transpose" :lib "core.matrix" :id 22 :doc "https://mikera.github.io/core.matrix/doc/clojure.core.matrix.html#var-transpose" :sig "-"}
"Elixir @transpose" {:lang "Elixir " :algo "transpose" :lib "Matrix" :id 22 :doc "https://hexdocs.pm/matrix/Matrix.html#transpose/1" :sig "-"}
"Scala@transpose" {:lang "Scala" :algo "transpose" :lib "various" :id 22 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#transpose" :sig "-"}
"D@transposed" {:lang "D" :algo "transposed" :lib "range" :id 22 :doc "https://dlang.org/library/std/range/transposed.html" :sig "-"}
;; "RAPIDS (Python)@transpose" {:lang "RAPIDS (Python)" :algo "transpose" :lib "DataFrame" :id 22 :doc "https://docs.rapids.ai/api/cudf/stable/api.html#cudf.core.dataframe.DataFrame.transpose" :sig "-"}
;; "RAPIDS (Python)@value_counts" {:lang "RAPIDS (Python)" :algo "value_counts" :lib "Series" :id 19 :doc "https://docs.rapids.ai/api/cudf/stable/api.html#cudf.core.series.Series.value_counts" :sig "-"}
"pandas@value_counts" {:lang "pandas" :algo "value_counts" :lib "Series" :id 19 :doc "https://pandas.pydata.org/pandas-docs/stable/reference/api/pandas.Series.value_counts.html" :sig "-"}
"Haskell@groupBy" {:lang "Haskell" :algo "groupBy" :lib "Data.List.HT" :id 23 :doc "https://hackage.haskell.org/package/utility-ht-0.0.15/docs/Data-List-HT.html#v:groupBy" :sig "-"}
"Clojure@partition-by" {:lang "Clojure" :algo "partition-by" :lib "core" :id 23 :doc "https://clojuredocs.org/clojure.core/partition-by" :sig "-"}
"Ruby@chunk_while" {:lang "Ruby" :algo "chunk_while" :lib "Enumerable" :id 23 :doc "https://apidock.com/ruby/Enumerable/chunk_while" :sig "-"}
"D@chunkBy" {:lang "D" :algo "chunkBy" :lib "algorith.iteration" :id 23 :doc "https://dlang.org/library/std/algorithm/iteration/chunk_by.html" :sig "-"}
"Elixir@chunk_while" {:lang "Elixir" :algo "chunk_while" :lib "Enum" :id 23 :doc "https://hexdocs.pm/elixir/Enum.html#chunk_while/4" :sig "-"}
"Ruby@slice_when" {:lang "Ruby" :algo "slice_when" :lib "Enumerable" :id 23 :doc "https://apidock.com/ruby/v2_5_5/Enumerable/slice_when" :sig "-"}
"C++@transform*" {:lang "C++" :algo "transform*" :lib "<algorithm>" :id 30 :doc "https://en.cppreference.com/w/cpp/algorithm/transform" :sig "-"}
"Haskell@zipWith" {:lang "Haskell" :algo "zipWith" :lib "Prelude" :id 30 :doc "https://hackage.haskell.org/package/base-4.14.0.0/docs/Prelude.html#v:zipWith" :sig "-"}
"Clojure@map*" {:lang "Clojure" :algo "map*" :lib "core" :id 30 :doc "https://clojuredocs.org/clojure.core/map" :sig "-"}
"Racket@map*" {:lang "Racket" :algo "map*" :lib "base" :id 30 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket%2Fprivate%2Fmap..rkt%29._map%29%29" :sig "-"}
"OCaml@map2" {:lang "OCaml" :algo "map2" :lib "List" :id 30 :doc "https://caml.inria.fr/pub/docs/manual-ocaml/libref/List.html" :sig "-"}
"F#@map2" {:lang "F#" :algo "map2" :lib "Seq" :id 30 :doc "https://msdn.microsoft.com/visualfsharpdocs/conceptual/list.map2%5b%27t1%2c%27t2%2c%27u%5d-function-%5bfsharp%5d" :sig "-"}
"C++@filter" {:lang "C++" :algo "filter" :lib "range-v3" :id 3 :doc "https://ericniebler.github.io/range-v3/index.html#tutorial-views" :sig "-"}
"C#@Where" {:lang "C#" :algo "Where" :lib "Enumerable" :id 3 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.linq.enumerable.where?view=netcore-3.1" :sig "-"}
"J@copy" {:lang "J" :algo "copy" :lib "-" :id 3 :doc "https://code.jsoftware.com/wiki/Vocabulary/number#dyadic" :sig "-"}
"q@where" {:lang "q" :algo "where" :lib "-" :id 3 :doc "https://code.kx.com/q4m3/A_Built-in_Functions/#a107-where" :sig "-"}
"Java@filter" {:lang "Java" :algo "filter" :lib "Streams" :id 3 :doc "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#filter-java.util.function.Predicate-" :sig "-"}
"OCaml@filter" {:lang "OCaml" :algo "filter" :lib "List" :id 3 :doc "https://ocaml.org/releases/4.10/htmlman/libref/List.html" :sig "-"}
"Clojure@filter" {:lang "Clojure" :algo "filter" :lib "core" :id 3 :doc "https://clojuredocs.org/clojure.core/filter" :sig "-"}
"Racket@filter" {:lang "Racket" :algo "filter" :lib "base" :id 3 :doc "https://docs.racket-lang.org/reference/pairs.html?q=filter#%28def._%28%28lib._racket%2Fprivate%2Flist..rkt%29._filter%29%29" :sig "-"}
"F#@filter" {:lang "F#" :algo "filter" :lib "List" :id 3 :doc "https://msdn.microsoft.com/visualfsharpdocs/conceptual/list.filter%5b%27t%5d-function-%5bfsharp%5d" :sig "-"}
"D@filter" {:lang "D" :algo "filter" :lib "algorithm" :id 3 :doc "https://dlang.org/library/std/algorithm/iteration/filter.html" :sig "-"}
"Ruby@filter" {:lang "Ruby" :algo "filter" :lib "Array" :id 3 :doc "https://apidock.com/ruby/Array/filter" :sig "-"}
"Python@filter" {:lang "Python" :algo "filter" :lib "-" :id 3 :doc "https://docs.python.org/3/library/functions.html#filter" :sig "-"}
"Kotlin@filter" {:lang "Kotlin" :algo "filter" :lib "collections" :id 3 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/filter.html" :sig "-"}
"Scala@filter" {:lang "Scala" :algo "filter" :lib "various" :id 3 :doc "https://www.scala-lang.org/api/2.12.4/scala/collection/immutable/Vector.html#filter(p:A=%3EBoolean):Repr" :sig "-"}
"Rust@filter" {:lang "Rust" :algo "filter" :lib "iter" :id 3 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.filter" :sig "-"}
"Elixir@filter" {:lang "Elixir" :algo "filter" :lib "Enum" :id 3 :doc "https://hexdocs.pm/elixir/Enum.html#filter/2" :sig "-"}
"Rust@counts" {:lang "Rust" :algo "counts" :lib "Itertools" :id 19 :doc "https://docs.rs/itertools/latest/itertools/trait.Itertools.html#method.counts" :sig ""}


;; "Haskell@-" {:lang "Haskell" :algo "-" :lib "" :id 100 :doc "" :sig ""}
;; "F#@mutable" {:lang "F#" :algo "mutable" :lib "" :id 100 :doc "https://en.wikibooks.org/wiki/F_Sharp_Programming/Mutable_Data" :sig ""}
;; "Rust@mut" {:lang "Rust" :algo "mut" :lib "" :id 100 :doc "https://doc.rust-lang.org/std/keyword.mut.html" :sig ""}
;; "OCaml@mutable" {:lang "OCaml" :algo "mutable" :lib "" :id 100 :doc "https://ocaml.org/releases/4.10/htmlman/objectexamples.html#s%3Aclasses-and-objects" :sig ""}
;; "Scala@var/val" {:lang "Scala" :algo "var/val" :lib "" :id 101 :doc "https://docs.scala-lang.org/tour/basics.html" :sig ""}
;; "Groovy@@Immutable" {:lang "Groovy" :algo "@Immutable" :lib "" :id 102 :doc "" :sig ""}
;; "C++@const" {:lang "C++" :algo "const" :lib "" :id 102 :doc "https://en.cppreference.com/w/cpp/language/cv" :sig ""}
;; "JavaScript@var/let/const" {:lang "JavaScript" :algo "var/let/const" :lib "" :id 101 :doc "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const" :sig ""}
;; "TypeScript@var/let/const" {:lang "TypeScript" :algo "var/let/const" :lib "" :id 101 :doc "https://www.typescriptlang.org/docs/handbook/variable-declarations.html" :sig ""}
;; "Kotlin@var/val" {:lang "Kotlin" :algo "var/val" :lib "" :id 101 :doc "https://kotlinlang.org/docs/tutorials/kotlin-for-py/declaring-variables.html" :sig ""}
;; "Swift@var/let" {:lang "Swift" :algo "var/let" :lib "" :id 101 :doc "https://docs.swift.org/swift-book/ReferenceManual/Declarations.html#grammar_constant-declaration" :sig ""}
;; "D@immutable/const" {:lang "D" :algo "immutable/const" :lib "" :id 102 :doc "https://dlang.org/spec/const3.html" :sig ""}
;; "Ruby@.freeze" {:lang "Ruby" :algo ".freeze" :lib "" :id 102 :doc "https://ruby-doc.org/core-2.7.1/Object.html#method-i-freeze" :sig ""}
;; "Erlang@-" {:lang "Erlang" :algo "-" :lib "" :id 100 :doc "" :sig ""}
;; "Elixir@-" {:lang "Elixir" :algo "-" :lib "" :id 100 :doc "" :sig ""}
;; "C#@const" {:lang "C#" :algo "const" :lib "" :id 102 :doc "https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/const" :sig ""}
;; "Go@var/const" {:lang "Go" :algo "var/const" :lib "" :id 101 :doc "https://gobyexample.com/constants" :sig ""}

"D@cartesianProduct" {:lang "D" :algo "cartesianProduct" :lib "std.algorithm.setops" :id 100 :doc "https://dlang.org/library/std/algorithm/setops/cartesian_product.html" :sig ""}
"D@dotProduct" {:lang "D" :algo "dotProduct" :lib "std.numeric" :id 7 :doc "https://dlang.org/library/std/numeric/dot_product.html" :sig ""}
"C++@cartesian_product" {:lang "C++" :algo "cartesian_product" :lib "range-v3" :id 100 :doc "https://ericniebler.github.io/range-v3/index.html#tutorial-views" :sig ""}
"Python@product" {:lang "Python" :algo "product" :lib "itertools" :id 100 :doc "" :sig ""}
"q@cross" {:lang "q" :algo "cross" :lib "-" :id 100 :doc "https://code.kx.com/q4m3/A_Built-in_Functions/#a18-cross" :sig ""}
"J@. (Matrix Product)" {:lang "J" :algo ". (Matrix Product)" :lib "-" :id 7 :doc "https://code.jsoftware.com/wiki/Vocabulary/dot#dyadic" :sig ""}
"R@outer" {:lang "R" :algo "outer" :lib "-" :id 6 :doc "https://www.rdocumentation.org/packages/base/versions/3.6.2/topics/outer" :sig ""}
"Ruby@product" {:lang "Ruby" :algo "product" :lib "Array" :id 100 :doc "https://apidock.com/ruby/Array/product" :sig ""}
"Racket@cartesian-product" {:lang "Racket" :algo "cartesian-product" :lib "base" :id 100 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket%2Flist..rkt%29._cartesian-product%29%29" :sig ""}
"Rust@cartesian_product" {:lang "Rust" :algo "cartesian_product" :lib "trait.itertools" :id 100 :doc "https://docs.rs/itertools/0.8.2/itertools/trait.Itertools.html#method.cartesian_product" :sig ""}

"C++@all_of" {:lang "C++" :algo "all_of" :lib "<algorithm>" :id 31 :doc "https://en.cppreference.com/w/cpp/algorithm/all_any_none_of" :sig ""}
"Haskell@all" {:lang "Haskell" :algo "all" :lib "Prelude" :id 31 :doc "https://hackage.haskell.org/package/base-4.14.0.0/docs/GHC-List.html#v:all" :sig ""}
"Clojure@every?" {:lang "Clojure" :algo "every?" :lib "core" :id 31 :doc "https://clojuredocs.org/clojure.core/every_q" :sig ""}
"Kotlin@all" {:lang "Kotlin" :algo "all" :lib "sequences" :id 31 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/all.html" :sig ""}
"Rust@all" {:lang "Rust" :algo "all" :lib "iter" :id 31 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.all" :sig ""}
"Scala@forall" {:lang "Scala" :algo "forall" :lib "various" :id 31 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.all" :sig ""}
"Elixir@all?" {:lang "Elixir" :algo "all?" :lib "Enum" :id 31 :doc "https://hexdocs.pm/elixir/Enum.html#all?/2" :sig ""}
"Python@all" {:lang "Python" :algo "all" :lib "builtin" :id 31 :doc "https://docs.python.org/3/library/functions.html#all" :sig ""}
"Ruby@all" {:lang "Ruby" :algo "all" :lib "Enumerable" :id 31 :doc "https://apidock.com/ruby/Enumerable/all%3F" :sig ""}
"Java@allMatch" {:lang "Java" :algo "allMatch" :lib "Streams" :id 31 :doc "https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#allMatch-java.util.function.Predicate-" :sig ""}
"D@all" {:lang "D" :algo "all" :lib "std.algorithm" :id 31 :doc "https://dlang.org/library/std/algorithm/searching/all.all.html" :sig ""}
"Swift@allSatisfy" {:lang "Swift" :algo "allSatisfy" :lib "" :id 31 :doc "https://developer.apple.com/documentation/swift/array/2994715-allsatisfy" :sig ""}
"OCaml@for_all" {:lang "OCaml" :algo "for_all" :lib "List" :id 31 :doc "https://caml.inria.fr/pub/docs/manual-ocaml/libref/List.html" :sig ""}
"C#@All" {:lang "C#" :algo "All" :lib "Linq" :id 31 :doc "https://docs.microsoft.com/en-us/dotnet/api/system.linq.enumerable.all?view=netcore-3.1" :sig ""}
"JavaScript@every" {:lang "JavaScript" :algo "every" :lib "" :id 31 :doc "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/every" :sig ""}
"Elm@all" {:lang "Elm" :algo "all" :lib "List" :id 31 :doc "https://package.elm-lang.org/packages/elm/core/latest/List#all" :sig ""}
"Julia@all" {:lang "Julia" :algo "all" :lib "Base" :id 31 :doc "https://docs.julialang.org/en/v1/base/collections/#Base.all-Tuple{AbstractArray,Any}" :sig ""}
"F#@allPairs" {:lang "F#" :algo "allPairs" :lib "List" :id 100 :doc "https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-listmodule.html#allPairs" :sig ""}
"Julia@product" {:lang "Julia" :algo "product" :lib "IterTools" :id 100 :doc "https://juliacollections.github.io/IterTools.jl/v0.2.1/#product(xs...)-1" :sig ""}
"APL@,∘.," {:lang "APL" :algo ",∘.," :lib "" :id 100 :doc "" :sig ""}
"Python@all_equal" {:lang "Python" :algo "all_equal" :lib "more-itertools" :id 51 :doc "https://more-itertools.readthedocs.io/en/stable/api.html#more_itertools.all_equal" :sig ""}
"Clojure@apply = " {:lang "Clojure" :algo "apply = " :lib "" :id 51 :doc "" :sig ""}
"Racket@apply = " {:lang "Racket" :algo "apply = " :lib "" :id 51 :doc "" :sig ""}
"Haskell@allEqual" {:lang "Haskell" :algo "allEqual" :lib "Data.List.HT" :id 51 :doc "" :sig ""}
"JavaScript@allEqual" {:lang "JavaScript" :algo "allEqual" :lib "bbo" :id 51 :doc "https://tnfe.github.io/bbo/#allequal" :sig ""}
"C++@rotate" {:lang "C++" :algo "rotate" :lib "<algorithm>" :id 4104 :doc "https://en.cppreference.com/w/cpp/algorithm/rotate" :sig ""}
"APL@⌽ (rotate)" {:lang "APL" :algo "⌽ (rotate)" :lib "" :id 4104 :doc "https://microapl.com/apl_help/ch_020_020_520.htm" :sig ""}
"J@|. (rotate)" {:lang "J" :algo "|. (rotate)" :lib "" :id 4104 :doc "https://code.jsoftware.com/wiki/Vocabulary/bardot#dyadic" :sig ""}
"Ruby@rotate" {:lang "Ruby" :algo "rotate" :lib "Array" :id 4104 :doc "https://apidock.com/ruby/v2_5_5/Array/rotate" :sig ""}
"Rust@rotate_left" {:lang "Rust" :algo "rotate_left" :lib "Slice" :id 4104 :doc "https://doc.rust-lang.org/stable/std/primitive.slice.html#method.rotate_left" :sig ""}
"Java@rotate" {:lang "Java" :algo "rotate" :lib "Collections" :id 4104 :doc "https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#rotate(java.util.List,%20int)" :sig ""}
"Julia@circshift" {:lang "Julia" :algo "circshift" :lib "" :id 4104 :doc "https://docs.julialang.org/en/v1/base/arrays/#Base.circshift" :sig ""}
"q@rotate" {:lang "q" :algo "rotate" :lib "Core" :id 4104 :doc "https://code.kx.com/q4m3/A_Built-in_Functions/#a79-rotate" :sig ""}
"Rust@dedup" {:lang "Rust" :algo "dedup" :lib "itertools" :id 15 :doc "https://docs.rs/itertools/0.7.6/itertools/structs/struct.Dedup.html" :sig ""}
"Racket@remove-duplicates" {:lang "Racket" :algo "remove-duplicates" :lib "" :id 15 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28lib._racket/list..rkt%29._remove-duplicates%29%29" :sig ""}
"OCaml@sort_uniq" {:lang "OCaml" :algo "sort_uniq" :lib "List" :id 15 :doc "https://ocaml.org/api/List.html#VALsort_uniq" :sig ""}
"Elm@dedup" {:lang "Elm" :algo "dedup" :lib "List" :id 14 :doc "https://package.elm-lang.org/packages/mgold/elm-nonempty-list/latest/List-Nonempty#dedup" :sig ""}
"Elm@uniq" {:lang "Elm" :algo "uniq" :lib "List" :id 15 :doc "https://package.elm-lang.org/packages/mgold/elm-nonempty-list/latest/List-Nonempty#uniq" :sig ""}
"F#@distinct" {:lang "F#" :algo "distinct" :lib "Seq" :id 15 :doc "https://fsharp.github.io/fsharp-core-docs/reference/fsharp-collections-seqmodule.html#distinct" :sig ""}
"Pharo@removeDuplicates" {:lang "Pharo" :algo "removeDuplicates" :lib "OrderedCollection" :id 15 :doc "" :sig ""}
"BQN@` (scan)" {:lang "BQN" :algo "` (scan)" :lib "-" :id 4 :doc "https://mlochbaum.github.io/BQN/help/scan.html" :sig ""}
"BQN@˝ (insert)" {:lang "BQN" :algo "˝ (insert)" :lib "-" :id 1 :doc "https://mlochbaum.github.io/BQN/help/insert.html" :sig ""}
"Kotlin@fold" {:lang "Kotlin" :algo "fold" :lib "collections" :id 1 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/fold.html" :sig ""}
"Kotlin@runningFold" {:lang "Kotlin" :algo "runningFold" :lib "collections" :id 4 :doc "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/running-fold.html" :sig ""}
"D@fold" {:lang "D" :algo "fold" :lib "algorithm.iteration" :id 1 :doc "https://dlang.org/library/std/algorithm/iteration/fold.html" :sig ""}
"Scala@fold" {:lang "Scala" :algo "fold" :lib "various" :id 1 :doc "https://www.scala-lang.org/api/2.12.8/scala/collection/immutable/Vector.html#fold[A1%3E:A](z:A1)(op:(A1,A1)=%3EA1):A1" :sig ""}
"Scala@reduce" {:lang "Scala" :algo "reduce" :lib "various" :id 1 :doc "https://www.scala-lang.org/api/2.12.8/scala/collection/immutable/Vector.html#reduce[A1%3E:A](op:(A1,A1)=%3EA1):A1" :sig ""}
"Ruby@inject" {:lang "Ruby" :algo "inject" :lib "Enumerable" :id 1 :doc "https://apidock.com/ruby/Enumerable/inject" :sig ""}
"Pharo@inject:into:" {:lang "Pharo" :algo "inject:into:" :lib "Collection" :id 1 :doc "" :sig ""}
"Pharo@reduce:" {:lang "Pharo" :algo "reduce:" :lib "Collection" :id 1 :doc "" :sig ""}
"Pharo@fold:" {:lang "Pharo" :algo "fold:" :lib "Collection" :id 1 :doc "" :sig ""}
"BQN@´ (fold)" {:lang "BQN" :algo "´ (fold)" :lib "" :id 1 :doc "https://mlochbaum.github.io/BQN/help/fold.html" :sig ""}
"APL@⌿ (reduce first)" {:lang "APL" :algo "⌿ (reduce first)" :lib "" :id 1 :doc "https://microapl.com/apl_help/ch_020_020_810.htm" :sig ""}
"BQN@⌜ (table)" {:lang "BQN" :algo "⌜ (table)" :lib "" :id 6 :doc "https://mlochbaum.github.io/BQN/help/table.html" :sig ""}
"J@/ (table)" {:lang "J" :algo "/ (table)" :lib "" :id 6 :doc "https://code.jsoftware.com/wiki/Vocabulary/slash#dyadic" :sig ""}





;; "Scala@val" {:lang "Scala" :algo "val" :lib "" :id 200 :doc "https://docs.scala-lang.org/tour/basics.html" :sig ""}
;; "Groovy@immutable" {:lang "Groovy" :algo "immutable" :lib "" :id 200 :doc "" :sig ""}
;; "C++@const" {:lang "C++" :algo "const" :lib "" :id 200 :doc "https://en.cppreference.com/w/cpp/language/cv" :sig ""}
;; "JavaScript@const" {:lang "JavaScript" :algo "const" :lib "" :id 200 :doc "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const" :sig ""}
;; "TypeScript@const" {:lang "TypeScript" :algo "const" :lib "" :id 200 :doc "https://www.typescriptlang.org/docs/handbook/variable-declarations.html" :sig ""}
;; "Kotlin@val" {:lang "Kotlin" :algo "val" :lib "" :id 200 :doc "https://kotlinlang.org/docs/tutorials/kotlin-for-py/declaring-variables.html" :sig ""}
;; "Swift@let" {:lang "Swift" :algo "let" :lib "" :id 200 :doc "https://docs.swift.org/swift-book/ReferenceManual/Declarations.html#grammar_constant-declaration" :sig ""}
;; "D@immutable" {:lang "D" :algo "immutable" :lib "" :id 200 :doc "https://dlang.org/spec/const3.html" :sig ""}
;; "Ruby@.freeze" {:lang "Ruby" :algo ".freeze" :lib "" :id 200 :doc "https://ruby-doc.org/core-2.7.1/Object.html#method-i-freeze" :sig ""}
;; "C#@const" {:lang "C#" :algo "const" :lib "" :id 200 :doc "https://docs.microsoft.com/en-us/dotnet/csharp/language-reference/keywords/const" :sig ""}
;; "Go@const" {:lang "Go" :algo "const" :lib "" :id 200 :doc "https://gobyexample.com/constants" :sig ""}

;; "C++@auto" {:lang "C++" :algo "auto" :lib "" :id 1729 :doc "" :sig ""}
;; "Python@def" {:lang "Python" :algo "def" :lib "" :id 1729 :doc "" :sig ""}
;; "Clojure@defn" {:lang "Clojure" :algo "defn" :lib "" :id 1729 :doc "" :sig ""}
;; "Racket@define" {:lang "Racket" :algo "define" :lib "" :id 1729 :doc "" :sig ""}
;; "Kotlin@fun" {:lang "Kotlin" :algo "fun" :lib "" :id 1729 :doc "" :sig ""}
;; "Rust@fn" {:lang "Rust" :algo "fn" :lib "" :id 1729 :doc "" :sig ""}
;; "Swift@func" {:lang "Swift" :algo "func" :lib "" :id 1729 :doc "" :sig ""}
;; "JavaScript@function" {:lang "JavaScript" :algo "function" :lib "" :id 1729 :doc "" :sig ""}
;; "Elixir@def" {:lang "Elixir" :algo "def" :lib "" :id 1729 :doc "" :sig ""}
;; "Scala@def" {:lang "Scala" :algo "def" :lib "" :id 1729 :doc "" :sig ""}
;; "Ruby@def" {:lang "Ruby" :algo "def" :lib "" :id 1729 :doc "" :sig ""}
;; "Julia@function" {:lang "Julia" :algo "function" :lib "" :id 1729 :doc "" :sig ""}
;; "LISP@defun" {:lang "LISP" :algo "defun" :lib "" :id 1729 :doc "" :sig ""}
;; "Groovy@def" {:lang "Groovy" :algo "def" :lib "" :id 1729 :doc "" :sig ""}
;; "Zig@fn" {:lang "Zig" :algo "fn" :lib "" :id 1729 :doc "" :sig ""}
;; "Nim@func" {:lang "Nim" :algo "func" :lib "" :id 1729 :doc "" :sig ""}
;; "Go@func" {:lang "Go" :algo "func" :lib "" :id 1729 :doc "" :sig ""}
;; "Fortran@function" {:lang "Fortran" :algo "function" :lib "" :id 1729 :doc "" :sig ""}
;; "Lua@function" {:lang "Lua" :algo "function" :lib "" :id 1729 :doc "" :sig ""}
;; "Crystal@def" {:lang "Crystal" :algo "def" :lib "" :id 1729 :doc "" :sig ""}

"Haskell@++" {:lang "Haskell" :algo "++" :lib "Prelude" :id 42 :doc "https://hackage.haskell.org/package/base-4.14.0.0/docs/Prelude.html#v:-43--43-" :sig ""}
"Racket@append" {:lang "Racket" :algo "append" :lib "base" :id 42 :doc "https://docs.racket-lang.org/reference/pairs.html#%28def._%28%28quote._~23~25kernel%29._append%29%29" :sig ""}
"Python@+" {:lang "Python" :algo "+" :lib "List" :id 42 :doc "https://docs.python.org/3/tutorial/introduction.html#lists" :sig ""}
"Clojure@concat" {:lang "Clojure" :algo "concat" :lib "List" :id 42 :doc "https://clojuredocs.org/clojure.core/concat" :sig ""}
"C++@join" {:lang "C++" :algo "join" :lib "Ranges" :id 42 :doc "https://en.cppreference.com/w/cpp/ranges" :sig ""}
"Swift@+" {:lang "Swift" :algo "+" :lib "Array" :id 42 :doc "https://developer.apple.com/documentation/swift/array/2963457" :sig ""}
"Rust@chain" {:lang "Rust" :algo "chain" :lib "iter" :id 42 :doc "https://doc.rust-lang.org/std/iter/trait.Iterator.html#method.chain" :sig ""}
"Python@chain" {:lang "Python" :algo "chain" :lib "itertools" :id 42 :doc "https://docs.python.org/3/library/itertools.html#itertools.chain" :sig ""}
"Julia@vcat" {:lang "Julia" :algo "vcat" :lib "Base" :id 42 :doc "https://docs.julialang.org/en/v1/base/arrays/#Base.vcat" :sig ""}
"Elixir@concat" {:lang "Elixir" :algo "concat" :lib "Enum" :id 42 :doc "https://hexdocs.pm/elixir/Enum.html#concat/2" :sig ""}
"Elixir@++" {:lang "Elixir" :algo "++" :lib "Kernel" :id 42 :doc "https://hexdocs.pm/elixir/Kernel.html#++/2" :sig ""}



   })

(def logo-map
  {"APL"        "apl_logo.png"
   "BASH"       "bash_logo.png"
   "BQN"        "bqn_logo.svg"
   "C++"        "cpp_logo.png"
   "C#"         "csharp_logo.png"
   "Clojure"    "clojure_logo.png"
   "Crystal"    "crystal_logo.svg"
   "CUDA"       "thrust_logo.jfif"
   "D"          "d_logo.png"
   "Elixir"     "elixir_logo.png"
   "Elm"        "elm_logo.png"
   "Erlang"     "erlang_logo.png"
   "F#"         "fsharp_logo.png"
   "Fortran"    "fortran_logo.png"
   "Go"         "go_logo.png"
   "Groovy"     "groovy_logo.jpeg"
   "Haskell"    "haskell_logo.svg"
   "J"          "j_logo.png"
   "Java"       "java_logo.png"
   "JavaScript" "javascript_logo.png"
   "Julia"      "julia_logo.svg"
   "Kotlin"     "kotlin_logo.png"
   "LISP"       "LISP_logo.png"
   "Lua"        "lua_logo.png"
   "Nim"        "nim_logo.png"
   "OCaml"      "ocaml_logo.jpg"
   "q"          "kx-logo.png"
   "pandas"     "pandas-logo3.png"
   "Pharo"      "pharo_logo.png"
   "Python"     "python_logo.png"
   "R"          "r_logo.png"
   "Racket"     "racket_logo.png"
   "RAPIDS (C++)"  "rapids_logo.png"
   "RAPIDS (Python)"  "rapids_logo.png" ;; TODO longterm fix
   "Ruby"       "ruby_logo.png"
   "Rust"       "rust_logo.png"
   "Scala"      "scala_logo2.png"
   "Swift"      "swift_logo.png"
   "TypeScript" "ts.png"
   "Zig"        "zig_logo.svg"})

; (defn extract-lang [s]
;   (first (str/split s #"@")))

(defn normalize-algo [algo]
  (->> algo
       (re-seq #"[a-zA-Z0-9]")
       (str/join)
       (str/lower-case)))

(defn extract-algo [s] ;; TODO this is confusingly named (returns a list)
  [(-> s
       (str/split #"@")
       (last)
       (normalize-algo)) s])

(defn first-equals [s [a _]]
  (= a s))

(defn filter-by-algo [algo m]
  (map last (filter (partial first-equals (normalize-algo algo)) (map extract-algo (keys m)))))

(defn get-id   [m] (get m :id))
(defn get-algo [m] (get m :algo))
(defn get-lang [m] (get m :lang))
(defn get-sig  [m] (get m :sig))

(defn filter-by-algo-id [id m]
  (map last (filter (partial first-equals id)
                    (map vector (map get-id (vals m)) (keys m)))))

(defn filter-by-algo-id2 [id m]
  (keep (fn [[k {elem-id :id}]] (when (= id elem-id) k)) m))

(defn filter-by-algo-id3 [id m]
  (->> m 
       (map (fn [[k {elem-id :id}]] (when (= id elem-id) k)))
       (remove nil?)))

(defn filter-by-algo-id4 [id m]
  (->> m 
       (filter (fn [[_ {elem-id :id}]] (= id elem-id)))
       (map first)))


;; (keep (fn [[k {elem-id :id}]] (when (= id elemid) k)) m)

(defn filter-by-lang [lang m]
  (map last (filter (partial first-equals lang)
                    (map vector (map get-lang (vals m)) (keys m)))))

(defn filter-by-sig [sig m]
  (map last (filter (partial first-equals sig)
                    (map vector (map get-sig (vals m)) (keys m)))))

(def tr-hover-style {::stylefy/mode {:hover {:background-color "purple"}}})

(declare generate-table)

(def excel-colors ["#CC99FF" "#99CCFF" "#CCFFCC" ;"#CCFFFF"
                   "#FFFF99" "#FFCC99" "#FF99CC" "white"]) 

(defn generate-row [info-map color-index]
  [:tr 
  ;  (use-style tr-hover-style)
  ;  {:style (use-style tr-hover-style)}
    ; {:style [:&:hover {:background-color "#f1f1f1"}]}
  ;  []^{:pseudo {:hover {:background-color "purple"}}}
  ;  {:style {:tr:hover {:background-color "purple"}}}
  ;  {:.item:hover {:background-color "#f5f5f5"}}
  {:on-click
   (fn [_] ((reset! state {:top-padding "20px"
                           :results-table (generate-table (get info-map :id) :by-algo-id)})))

   ::stylefy/mode {:on-hover {:background-color "purple"}} ;; TODO make work
   }
   [:td [:img {:src (str/join ["/media/" (get logo-map (get info-map :lang))]) :width "40px" :height "40px"}]]
   [:td {:style {:padding "12px 30px"}} (get info-map :lang)]
   [:td {:style {:padding "12px 30px"
                 :font-weight "bold"
                 :background-color (nth excel-colors color-index)
                ;  :border "2px solid black"
                 }}  (get info-map :algo)]
   [:td {:style {:padding "12px 30px"}} (get info-map :lib)]
   [:td {:style {:padding "12px 30px"}} [:a {:href (get info-map :doc)} "Doc"]]])

(defn choose-filter [how-to-generate-table]
  (case how-to-generate-table
    :by-algo    (partial filter-by-algo)
    :by-algo-id (partial filter-by-algo-id)
    :by-lang    (partial filter-by-lang)
    :by-sig     (partial filter-by-sig)))

(defn choose-colors [how-to-generate-table rows-info]
  (case how-to-generate-table
    
    :by-algo-id (map vector rows-info
                     (map (partial
                           get (->> (map get-algo rows-info)
                                    (map normalize-algo)
                                    (frequencies)
                                    (into (vector))
                                    (sort-by last >)
                                    (map-indexed (fn [i [algo _]] [algo (min i 6)]))
                                    (into (hash-map))))
                          (->> (map get-algo rows-info)
                               (map normalize-algo))))
    
    :by-algo    (map vector rows-info
                     (map (partial
                           get (->> (map get-id rows-info)
                                    (frequencies)
                                    (into (vector))
                                    (sort-by last >)
                                    (map-indexed (fn [i [id _]] [id (min i 6)]))
                                    (into (hash-map)))) (map get-id rows-info)))
    
    :by-lang    (map vector rows-info
                     (repeat (count rows-info) 0))
    
    :by-sig    (map vector rows-info
                    (repeat (count rows-info) 0))))

(defn in?
  [coll elm]
  (some #(= elm %) coll))

(defn decide-how [input]
  (cond 
    (in? (->> logo-map
              (keys)
              (map str/lower-case))
         (str/lower-case input)) :by-lang ;; TODO figure out why this lower-case isn't working
    (str/includes? input "->") :by-sig
    (str/includes? input " ")  :by-algo-id
    :else :by-algo))

(defn generate-table [selection how-to-generate-table]
  [:table {:style {:font-family "Consolas"
                   :padding "12px 12px"
                   :font-size "30"
                   :margin-left "auto"
                   :margin-right "auto"
                   :text-align "center"}}

   (->> by-key-map
        ((choose-filter how-to-generate-table) selection)
        (select-keys by-key-map)
        (vals)
        (choose-colors how-to-generate-table)
        (sort-by last)
        (map (partial apply generate-row)))

  ])

(defn app-view []
  [:div {:style {:search-text ""
                 :text-align "center"
                 :padding (@state :top-padding)}}
   [:label {:style {:color "darkviolet"
                    :font-family "Verdana"
                    :font-size "50"
                    :font-weight "bold"}} "Programming Language Rankings"]
   [:br]
   [:label (@debug :info)]
   [:br]
   [:input
    {:spellcheck "false"
     :focus true ;; TODO fix
     :style {:padding "12px 20px"
             :margin "8px 0"
             :width "600px"
             :font-size "30"
             :font-family "Consolas"
             :border-radius "25px"
             :border "2px solid grey"
             :outline-width "0"
             :text-align "center"
             :spellcheck "false"}
    ;  :value (@state :search-text) ;; TODO get search text to update on clicks
     :on-key-press
     (fn [e]
       (if (= (.-key e) "Enter")
         (reset! state {:search-text (.. e -target -value)
                        :top-padding "20px"
                        :results-table (generate-table (if (and (str/includes? (.. e -target -value) " ")
                                                                (not (str/includes? (.. e -target -value) "->")))
                                                         ( ->> (str/replace (.. e -target -value) " " "@")
                                                           (get by-key-map)
                                                           (get-id))
                                                         (.. e -target -value)) 
                                                       (decide-how (.. e -target -value)))})
         (.log js/console "Not Enter")))}]
   ; ((reset! state {:search-text (str/join [(@state :search-text) (-.key e)])}))))}]
   [:br]
   [:br]

   (@state :results-table)
   
   ])

(defn render! []
  (rdom/render
   [app-view]
   (js/document.getElementById "app")))



