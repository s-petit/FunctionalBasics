package com.spe.maybe;

import com.spe.functor.Monad;

import java.util.function.Function;

//Seems impossible to implement correcty Monad interface with java
public abstract class MaybeWithInterface<T> implements Monad<T, MaybeWithInterface<?>> {
    public abstract T fromJust();

    public abstract boolean isJust();
    public abstract boolean isNothing();
    public abstract <U> MaybeWithInterface<U> bind(Function<? super T, MaybeWithInterface<?>> mapping);

}
