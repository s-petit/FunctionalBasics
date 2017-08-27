package com.spe.applicative;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MyApplicativeTest {

    @Test
    public void should_apply() {
        MyApplicative h1Applicative = MyApplicative.of(surroundWithH1());
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);

        MyApplicative applied = myApplicative.apply(h1Applicative);
        assertThat(applied.get()).isEqualTo("<h1>42</h1>");
    }

    @Test
    public void should_apply_enhanced() {
        MyApplicative h1 = MyApplicative.of(surroundWithH1());
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);

        MyApplicative applied = myApplicative
                .fmap(x -> x + 13)
                .apply(h1)
                .fmap(x -> x + " for Real");
        assertThat(applied.get()).isEqualTo("<h1>55</h1> for Real");
    }

    @Test
    public void should_fmap() {
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);

        MyApplicative applied = myApplicative
                .fmap(x -> x + 13)
                .fmap(x -> x + 10)
                .fmap(x -> x + 5);

        assertThat(applied.get()).isEqualTo(70);
    }

    @Test
    public void apply_should_stuck() {
        MyApplicative wrappedH1 = MyApplicative.of(wrapApplicative());
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);

        MyApplicative applied = myApplicative
                .fmap(x -> x + 13)
                .apply(wrappedH1)
                .fmap(x -> x + " for Real");
        // can't chain fmap/apply when apply or fmap use a function x -> Applicative(x)
        assertThat(applied.get()).isNotEqualTo("<h1>55</h1> for Real");
    }

    private Function<Object, String> surroundWithH1() {
        return x -> "<h1>" + x + "</h1>";
    }

    private Function<Object, Applicative> wrapApplicative() {
        return x -> MyApplicative.of("<h1>" + x + "</h1>");
    }

}