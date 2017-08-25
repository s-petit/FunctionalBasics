package com.spe.functor;

import java.util.Objects;
import java.util.function.Function;

public class HashApplicative<T> implements Applicative<T, HashApplicative> {

    // In an Applicative, T could be a Function
    private final T value;

    private HashApplicative(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> HashApplicative<T> of(T value) {
        return new HashApplicative<>(value);
    }

    public T get() {
        return value;
    }

    public HashApplicative<String> hash() {
        String strVal = value.toString();
        return HashApplicative.of(HashFunctor.getStringHashFunctor(strVal));
    }

    @Override
    public <B> HashApplicative<B> pure(B b) {
        return HashApplicative.of(b);
    }

    @Override
    public <B> HashApplicative<B> apply(Applicative<Function<? super T, ? extends B>, HashApplicative> appFn) {
        //appFn is an Applicative which contains a function
        Function<? super T, ? extends B> applicativeFunction = ((HashApplicative<Function<? super T, ? extends B>>) appFn).value;
        return HashApplicative.of(applicativeFunction.apply(this.value));
    }

    @Override
    public <B> HashApplicative<B> fmap(Function<? super T, ? extends B> fn) {
        B apply = fn.apply(value);
        return HashApplicative.of(apply);
    }

}
