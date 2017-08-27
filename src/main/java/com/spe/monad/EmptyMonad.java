package com.spe.monad;

import com.spe.maybe.Monad;

import java.util.function.Function;

public class EmptyMonad implements Monad {

    public static final EmptyMonad INSTANCE = new EmptyMonad();

    private EmptyMonad() {
    }

    @Override
    public EmptyMonad bind(Function fn) {
        return INSTANCE;
    }

}
