# FunctionalBasics

Articles intéressants sur le sujet

http://jnape.com/the-perils-of-implementing-functor-in-java/
http://www.leonardmeyer.com/blog/2014/06/functors-applicatives-et-monads-en-images/
https://stackoverflow.com/questions/7369460/help-with-understanding-a-function-object-or-functor-in-java
https://drboolean.gitbooks.io/mostly-adequate-guide/content/
http://learnyouahaskell.com/functors-applicative-functors-and-monoids
https://pbrisbin.com/posts/applicative_functors/
https://bartoszmilewski.com/2015/01/20/functors/
https://dzone.com/articles/whats-wrong-java-8-part-iv
https://medium.com/@sinisalouc/demystifying-the-monad-in-scala-cc716bb6f534
https://www.sitepoint.com/how-optional-breaks-the-monad-laws-and-why-it-matters/
https://dzone.com/articles/functor-and-monad-examples-in-plain-java
http://scabl.blogspot.fr/2013/02/monads-in-scala-1.html
https://pbrisbin.com/posts/applicative_functors/
https://hackernoon.com/kotlin-functors-applicatives-and-monads-in-pictures-part-2-3-f99a09efd1ec
https://stackoverflow.com/questions/35951818/why-can-the-monad-interface-not-be-declared-in-java


**Functors**

Un functor est une type class / interface qui implémente forcément une fonction “fmap”. fmap accepte en paramètre une fonction. Cette fonction pourra être appliquée sur les objets de type Functor. Cette fonction changera la/les valeurs contenues dans le contexte fermé du functor.

a “fmap” operation: F<T> fmap T -> U = F<U>
Ou F est un Functor
T et U sont des type paramétrés
Exemple : Functor<Integer> fmap x -> x*2.toString() = Functor<String>

L’avantage est que l’on peut peut appliquer n’importe quelle fonction sur n’importe quel Functor. Et donc d’appliquer des fonction sur des types donnés, dont l’impact sera différent selon le contexte et selon le Functor.

**Applicatives**

Un Applicative est une type class / interface qui implémente forcément une fonction “apply”. apply accepte en paramètre une fonction boxée dans un contexte. Cette fonction pourra être appliquée sur les objets de type Applicative. Cette fonction changera la/les valeurs contenues dans le contexte fermé de l’applicative.

a “apply” operation: A<T> apply A<Function<T -> U>> = A<U>

Ou A est un Applicative
T et U sont des type paramétrés
Exemple : Applicative<Integer> apply Applicative<x -> x*2.toString()> = Applicative<String>

//def map[A, B](f : A => B): C[A] => C[B]
   //def apply[A, B](f: F[A => B]): F[A] => F[B]

**Monades**

Une Monade est une type class / interface qui implémente forcément une fonction “bind” / flatmap qui accepte en paramètre une fonction qui permet de convertir le type contenu dans la monade en un nouvelle monade d’un nouveau type. Cela retourne une nouvelle instance de cette monade, mais cette dernière contiendra un nouveau type.

L’avantage des Monades est de faire de la composition de fonctions, et pouvoir chaîner les appels et appliquer plusieurs fois des fonctions séquentiellenent (ex: map() de Stream)

Without entering the details of Category theory, a monad is a very simple, yet extremely powerful thing. A monad a set of three things:
a parameterized type M<T>
Exemple : Stream<String>
a “unit” function T -> M<T>
Exemple : Stream.of(“a”, “b”)
a “bind” operation: M<T> bind T -> M<U> = M<U>
Exemple : intStream.flatmap(x -> Stream.of(x+1)) ou intStream.flatmap(x -> Stream.of(new BigDecimal(x))
Remarque : map() est un sous ensemble de flatmap. On peut effectuer un map grace a flatmap mais pas l’inverse.
Une monade est forcement un functor et une applicative.
Une monade wrappe un type, lui apportant des comportements et du contexte supplémentaire (ex : Optional)

L’avantage est de pouvoir chainer et de faire des appels purement fonctionnels. Composition de fonctions.

def unit: A → F[A] avec A Integer
Integer -> Optional<Integer>
def map: A → B
Optional<Integer> -> Optional<String>

def flatten: F[F[A]] → F[A]
Optional<Optional<Integer>> -> Optional<Integer>
