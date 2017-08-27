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

    // maybe
    @Override
    public <U> Maybe<U> bind(Function<? super T, Maybe<U>> mapping) {
        return mapping.apply(mValue);
    }

    @Override
    public <U> Maybe<U> fmap(Function<? super T, U> mapping) {
        return bind(mapping.andThen(x -> new Just<U>(x)));
    }


    // N.B : map is a subset of flatMap
    /*
    def map[B](f: A => B): Maybe[B] = flatMap { a => Just(f(a)) }

    a -> f(a) puis a -> Just (a) avec a -> f(a)
        = Just(f(a)) GOOD


    a -> Just(a)
    puis a -> f(a) avec a -> Just(a)
        = f(Just(a))  WRONG
    */

    @Override
    public boolean isJust() {
        return true;
    }

}