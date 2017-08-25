package com.spe.maybe;


import java.util.function.Function;

public class Nothing<T> extends Maybe {

    @Override
    public T fromJust() throws RuntimeException {
        throw new RuntimeException("Cannot call fromJust() on Nothing");
    }

    @Override
    public boolean isJust() {
        return false;
    }

    @Override
    public boolean isNothing() {
        return true;
    }

    @Override
    public Maybe fmap(Function mapping) {
        return this;
    }

    @Override
    public Maybe bind(Function mapping) {
        return this;
    }


}
