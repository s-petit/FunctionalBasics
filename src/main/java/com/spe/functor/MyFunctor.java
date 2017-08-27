package com.spe.functor;

import java.util.Objects;
import java.util.function.Function;

public class MyFunctor<T> implements Functor<T, MyFunctor> {

    private final T value;

    private MyFunctor(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> MyFunctor<T> of(T value) {
        return new MyFunctor<>(value);
    }

    public T get() {
        return value;
    }

    @Override
    public <B> MyFunctor<B> fmap(Function<? super T, ? extends B> fn) {
        B apply = fn.apply(value);
        return MyFunctor.of(apply);
    }

}
