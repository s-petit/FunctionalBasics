package com.spe.person;

import java.util.function.Function;

public class DiedBoxed extends PersonBoxed {

    public static final DiedBoxed INSTANCE = new DiedBoxed();

    private DiedBoxed() {
    }

    @Override
    public DiedBoxed bind(Function fn) {
        return INSTANCE;
    }

}
