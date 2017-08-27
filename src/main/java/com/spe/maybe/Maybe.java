package com.spe.maybe;

import java.util.function.Function;

public abstract class Maybe<T> {

    // unbox operation : M<T> -> T
    public abstract T fromJust();
    public abstract boolean isJust();
    public abstract boolean isNothing();

    // bind operation : A -> M<B> = M<B>
    // bind is more or less like a flatmap
    public abstract <U> Maybe<U> bind(Function<? super T, Maybe<U>> mapping);

    // Functor-like fmap is a subset of bind
    public abstract <U> Maybe<U> fmap(Function<? super T, U> mapping);
}
