package com.spe.person;

import java.util.function.Function;

public class NotExistBoxed extends PersonBoxed {

    public static final NotExistBoxed INSTANCE = new NotExistBoxed();

    private NotExistBoxed() {
    }

    @Override
    public NotExistBoxed bind(Function fn) {
        return INSTANCE;
    }

}
