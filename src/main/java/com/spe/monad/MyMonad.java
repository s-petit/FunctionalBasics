package com.spe.monad;

import com.spe.maybe.Monad;

import java.util.Objects;
import java.util.function.Function;

public class MyMonad<T> implements Monad<T> {

    private final T value;

    private MyMonad(T value) {
        this.value = Objects.requireNonNull(value);
    }

    // Monad return : T1 -> M<T1>
    public static <T> MyMonad<T> of(T value) {
        return new MyMonad<>(value);
    }

    // Monad unbox : M<T> -> T
    public T get() {
        return value;
    }

    // monad bind : M<T> -> (T -> M<U>) -> M<U>
    @Override
    public <U> Monad<U> bind(Function<? super T, Monad<U>> mapping) {
        return mapping.apply(value);
    }

}
