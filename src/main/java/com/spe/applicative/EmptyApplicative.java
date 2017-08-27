package com.spe.applicative;

import java.util.function.Function;

public class EmptyApplicative<T> implements Applicative<T, EmptyApplicative> {

    public static final EmptyApplicative INSTANCE = new EmptyApplicative();

    private EmptyApplicative() {
    }

    @Override
    public <B> EmptyApplicative<B> pure(B b) {
        return INSTANCE;
    }

    @Override
    public <B> EmptyApplicative<B> apply(Applicative<Function<? super T, ? extends B>, EmptyApplicative> appFn) {
        return INSTANCE;
    }

    @Override
    public <B> EmptyApplicative<B> fmap(Function<? super T, ? extends B> fn) {
        return INSTANCE;
    }

}
