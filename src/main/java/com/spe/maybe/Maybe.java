package com.spe.maybe;

import java.util.function.Function;

public abstract class Maybe<T> {
    public abstract T fromJust();
    public abstract boolean isJust();
    public abstract boolean isNothing();
    public abstract <U> Maybe<U> bind(Function<? super T, Maybe<U>> mapping);
    //M flatMap(Function<T,M> f);

    public <U> Maybe<U> flatMap(Function<? super T, Maybe<U>> mapping) {
        return bind(mapping);
    }
    public abstract <U> Maybe<U> fmap(Function<? super T, U> mapping);
}
