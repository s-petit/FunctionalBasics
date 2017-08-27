package com.spe.applicative;

import java.util.Objects;
import java.util.function.Function;

public class MyApplicative<T> implements Applicative<T, MyApplicative> {

    // In an Applicative, T could be a Function
    private final T value;

    private MyApplicative(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> MyApplicative<T> of(T value) {
        return new MyApplicative<>(value);
    }

    public T get() {
        return value;
    }

    @Override
    public <B> MyApplicative<B> pure(B b) {
        return MyApplicative.of(b);
    }

    @Override
    public <B> MyApplicative<B> apply(Applicative<Function<? super T, ? extends B>, MyApplicative> appFn) {
        //appFn is an Applicative which contains a function
        Function<? super T, ? extends B> applicativeFunction = ((MyApplicative<Function<? super T, ? extends B>>) appFn).value;
        return MyApplicative.of(applicativeFunction.apply(this.value));
    }

    @Override
    public <B> MyApplicative<B> fmap(Function<? super T, ? extends B> fn) {
        B apply = fn.apply(value);
        return MyApplicative.of(apply);
    }

}
