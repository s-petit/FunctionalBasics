package com.spe.functor;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorApplicativeMonadTest {

    @Test
    public void functor_should_fmap_at_will() {
        HashFunctor<Person> personHash = HashFunctor.of(new Person("jean", new Person.Address("78120", "Rbt"), 18));

        int result = personHash
                .fmap(Person::getAddress)
                .fmap(address -> Integer.parseInt(address.getZip()))
                .fmap(zip -> zip + 80)
                .get();

        assertThat(result).isEqualTo(78200);
    }

    @Test
    public void functor_fmap_should_stuck_on_functions_which_returns_functors() {
        HashFunctor<Integer> myFunctor = HashFunctor.of(42);
        Function<Object, Functor> wrapIntoH1 = x -> HashFunctor.of("<h1>" + x + "</h1>");

        HashFunctor mapped = myFunctor
                .fmap(x -> x + 13)
                .fmap(wrapIntoH1);

        // can't chain fmap/apply when apply or fmap use a function x -> Functor(x)
        assertThat(mapped.get()).isNotExactlyInstanceOf(String.class);
        assertThat(mapped.get()).isNotEqualTo("<h1>55</h1>");

        //does not compile ! it's a shame we need Applicatives
/*
        HtmlFunctor huh = myFunctor
                .fmap(x -> x + 13)
                .fmap(wrapIntoH1)
                .fmap(x -> x.length);*/
    }

    @Test
    public void applicatives_should_resolve_our_issue() {
        HashApplicative<Integer> myApplicative = HashApplicative.of(42);

        HashApplicative<Function<? super Integer, ? extends String>> h1Applicative = HashApplicative.of((Function<Object, String>) x -> "<h1>" + x + "</h1>");
        HashApplicative mapped = myApplicative
                .fmap(x -> x + 13)
                .apply(h1Applicative);

        assertThat(mapped.get()).isInstanceOf(String.class);
        assertThat(mapped.get()).isEqualTo("<h1>55</h1>");

        //does compile now
        HashApplicative yay = myApplicative
                .fmap(x -> x + 13)
                .apply(h1Applicative)
                .fmap(String::length);

        assertThat(yay.get()).isEqualTo(11);
    }

    @Test
    public void applicatives_should_apply_at_will() {
        HashApplicative<Person> personHash = HashApplicative.of(new Person("jean", new Person.Address("78120", "Rbt"), 18));

        HashApplicative<Function<? super Person, ? extends Person.Address>> addressApplicative = HashApplicative.of(Person::getAddress);
        HashApplicative<Function<? super Person.Address, ? extends Integer>> parseZipApplicative = HashApplicative.of(ad -> Integer.parseInt(ad.getZip()));

        int result = personHash
                .apply(addressApplicative)
                .apply(parseZipApplicative)
                //could also be combined with fmap
                .fmap(zip -> zip + 80)
                .get();

        assertThat(result).isEqualTo(78200);

        // So, Applicatives are more powerful than Functors
        // but...
    }

    @Test
    public void apply_should_stuck() {

        HashApplicative wrappedH1 = HashApplicative.of((Function<Object,Applicative>) x -> HashApplicative.of("<h1>" + x + "</h1>"));
        HashApplicative<Integer> myApplicative = HashApplicative.of(42);

        HashApplicative applied = myApplicative
                .fmap(x -> x + 13)
                .apply(wrappedH1)
                .fmap(x -> x + " for Real");

        // can't chain fmap/apply when apply or fmap use a function x -> Applicative(x)
        // we need monads...
        assertThat(applied.get()).isNotExactlyInstanceOf(String.class);
        assertThat(applied.get()).isNotEqualTo("<h1>55</h1> for Real");
    }

    @Test
    public void monads_should_do_the_trick() {

        HashApplicative wrappedH1 = HashApplicative.of((Function<Object,Applicative>) x -> HashApplicative.of("<h1>" + x + "</h1>"));
        HashApplicative<Integer> myApplicative = HashApplicative.of(42);

        HashApplicative applied = myApplicative
                .fmap(x -> x + 13)
                .apply(wrappedH1)
                .fmap(x -> x + " for Real");

        // can't chain fmap/apply when apply or fmap use a function x -> Applicative(x)
        // we need monads...
        assertThat(applied.get()).isNotExactlyInstanceOf(String.class);
        assertThat(applied.get()).isNotEqualTo("<h1>55</h1> for Real");
    }

}
