package com.spe.functor;

import java.util.function.Function;

/**
 * An interface for the generic covariant functorial operation <code>map</code> over some parameter <code>A</code>.
 * Functors are foundational to many of the classes provided by this library; generally, anything that can be thought of
 * as "mappable" is an instance of at least this interface.
 * <p>
 * For more information, read about <a href="https://en.wikipedia.org/wiki/Functor" target="_top">Functors</a>.
 */
@FunctionalInterface
public interface Functor<A, F extends Functor> {

    /**
     * Covariantly transmute this functor's parameter using the given mapping function. Generally this method is
     * specialized to return an instance of the class implementing Functor.
     *
     * @param <B> the new parameter type
     * @param fn  the mapping function
     * @return a functor over B (the new parameter type)
     */
    <B> Functor<B, F> fmap(Function<? super A, ? extends B> fn);
}


//def map[A, B](f : A => B): C[A] => C[B]
    //def apply[A, B](f: F[A => B]): F[A] => F[B]


