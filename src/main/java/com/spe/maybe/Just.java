package com.spe.maybe;

import java.util.function.Function;

public class Just<T> extends Maybe<T> {
    private T mValue;

    public Just(T data) {
        mValue = data;
    }

    @Override
    public T fromJust() {
        return mValue;
    }

    @Override
    public boolean isNothing() {
        return false;
    }

    // monad
    @Override
    public <U> Maybe<U> bind(Function<? super T, Maybe<U>> mapping) {
        return mapping.apply(mValue);
    }

    /*

    @Override
    public <B> Monad<B> bind(Function<? super T, ? extends Monad> mapping) {
        return null;
    }
     */

    @Override
    public <U> Maybe<U> fmap(Function<? super T, U> mapping) {
        return bind(mapping.andThen(x -> new Just<U>(x)));
    }


/*    // >>=
    def flatMap[B](f: A => Maybe[B]): Maybe[B]

    // >>
    def map[B](f: A => B): Maybe[B] = flatMap { a => Just(f(a)) }

    a -> f(a) puis a -> Just (a) avec a -> f(a)
        = Just(f(a)) GOOD


    a -> Just(a)
    puis a -> f(a) avec a -> Just(a)
        = f(Just(a))  WRONG*/


    // functor
   /* @Override
    public <U> U map(Function<? super T, ? extends U> mapping) {
        return mapping.apply(mValue);
    }
*/
    //applicative ??


    @Override
    public boolean isJust() {
        return true;
    }

}