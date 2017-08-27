package com.spe.person;

import com.spe.maybe.Monad;

import java.util.Objects;
import java.util.function.Function;

public class AliveBoxed<T> extends PersonBoxed<T> {

    private final T value;

    private AliveBoxed(T value) {
        this.value = Objects.requireNonNull(value);
    }

    // Monad return : T1 -> M<T1>
    public static <T> AliveBoxed<T> of(T value) {
        return new AliveBoxed(value);
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

    //functor fmap : F<T> -> (T -> U) -> F<U>
    public <B> AliveBoxed<B> fmap(Function<? super T, ? extends B> fn) {
        B apply = fn.apply(value);
        return AliveBoxed.of(apply);
    }

    //applicative apply : A<T> -> A(T -> U) -> A<U>
    public <B> AliveBoxed<B> apply(AliveBoxed<Function<? super T, ? extends B>> appFn) {
        //appFn is an Applicative which contains a function
        Function<? super T, ? extends B> applicativeFunction = appFn.value;
        return AliveBoxed.of(applicativeFunction.apply(this.value));
    }

}
