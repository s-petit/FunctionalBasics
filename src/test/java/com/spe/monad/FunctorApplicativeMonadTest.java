package com.spe.monad;

import com.spe.person.NotExistBoxed;
import com.spe.person.Person;
import com.spe.applicative.MyApplicative;
import com.spe.functor.Functor;
import com.spe.functor.MyFunctor;
import com.spe.maybe.Monad;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctorApplicativeMonadTest {

    //def map[A, B](f : A => B): C[A] => C[B]
    //def apply[A, B](f: F[A => B]): F[A] => F[B]
    //def bind[A, B] (f : A => M[B]): M[A] => M[B]

    // Rule 1. we want to map boxed values, and chain them at will F<A> -> F<B> -> F<C> and so on, where F is a functor and A,B,C parametrized types
    // Rule 2. we want to map boxed values from/to every possible types, and chain them at will, even if the return type is itself F<A> -> F<F<B>> -> F<C>
    // Rule 3. we want to map boxed values from/to every possible types, and chain them at will, even if the return type is covariant F<A> -> G<B> -> H<C> where F,G,H are covariant

    // Rule 2 depends on Rule 1, and Rule 3 depends on Rule 2.


    // functors match rule 1.
    @Test
    public void functor_should_fmap_at_will() {
        MyFunctor<Person> personHash = MyFunctor.of(new Person("jean", new Person.Address("78120", "Rbt"), 18, NotExistBoxed.INSTANCE));

        int result = personHash
                .fmap(Person::getAddress)
                .fmap(address -> Integer.parseInt(address.getZip()))
                .fmap(zip -> zip + 80)
                .get();

        assertThat(result).isEqualTo(78200);
    }

    // functors struggle for rule 2 and 3.
    @Test
    public void functor_fmap_should_stuck_on_functions_which_returns_functors() {
        MyFunctor<Integer> myFunctor = MyFunctor.of(42);
        Function<Object, Functor> wrapIntoH1 = x -> MyFunctor.of("<h1>" + x + "</h1>");

        MyFunctor mapped = myFunctor
                .fmap(x -> x + 13)
                .fmap(wrapIntoH1);

        // can't chain fmap/apply when apply or fmap use a function x -> Functor(x)
        assertThat(mapped.get()).isNotExactlyInstanceOf(String.class);
        assertThat(mapped.get()).isNotEqualTo("<h1>55</h1>");

        //does not compile ! we need Applicatives
/*
        HtmlFunctor huh = myFunctor
                .fmap(x -> x + 13)
                .fmap(wrapIntoH1)
                .fmap(x -> x.length);*/
    }

    // applicatives match rule 1.
    @Test
    public void applicatives_should_apply_at_will() {
        MyApplicative<Person> personHash = MyApplicative.of(new Person("jean", new Person.Address("78120", "Rbt"), 18, NotExistBoxed.INSTANCE));

        MyApplicative<Function<? super Person, ? extends Person.Address>> addressApplicative = MyApplicative.of(Person::getAddress);
        MyApplicative<Function<? super Person.Address, ? extends Integer>> parseZipApplicative = MyApplicative.of(ad -> Integer.parseInt(ad.getZip()));

        int result = personHash
                .apply(addressApplicative)
                .apply(parseZipApplicative)
                //could also be combined with fmap
                .fmap(zip -> zip + 80)
                .get();

        assertThat(result).isEqualTo(78200);
    }

    // applicatives match rule 2.
    @Test
    public void applicatives_should_resolve_our_issue() {
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);

        MyApplicative<Function<? super Integer, ? extends String>> h1Applicative = MyApplicative.of((Function<Object, String>) x -> "<h1>" + x + "</h1>");
        MyApplicative mapped = myApplicative
                .fmap(x -> x + 13)
                .apply(h1Applicative);

        assertThat(mapped.get()).isInstanceOf(String.class);
        assertThat(mapped.get()).isEqualTo("<h1>55</h1>");

        //does compile now
        MyApplicative yay = myApplicative
                .fmap(x -> x + 13)
                .apply(h1Applicative)
                .fmap(String::length);

        assertThat(yay.get()).isEqualTo(11);

        // So, Applicatives are more powerful than Functors. We can flatten.
        // but... even with covariant applicatives types ?
    }

    // applicatives struggle for rule 3.
    @Test
    public void applicatives_struggle_when_applying_on_covariant_applicatives() {
        MyApplicative<Integer> myApplicative = MyApplicative.of(42);


        //does not compile ! we need Monads...
/*        MyApplicative yay = myApplicative
                .fmap(x -> x + 13)
                .apply(EmptyApplicative.INSTANCE)
                .apply(x -> x + "test");*/
    }


    // monads match rule 1 and 2.
    @Test
    public void monads_should_bind_at_will() {

        MyMonad<Integer> myMonad = MyMonad.of(42);

        Monad applied = myMonad
                .bind(x -> MyMonad.of(x + 13))
                .bind(x -> MyMonad.of("<h1>" + x + "</h1>"))
                .bind(x -> MyMonad.of(x + " for Real"));

        // it works !
        assertThat(applied).isInstanceOf(MyMonad.class);
        assertThat(((MyMonad) applied).get()).isEqualTo("<h1>55</h1> for Real");
    }

    // monads match rule 3.
    @Test
    public void monads_should_bind_covariants_monad() {

        MyMonad<Integer> myMonad = MyMonad.of(42);

        Monad applied = myMonad
                .bind(x -> MyMonad.of(x + 13))
                .bind(x -> EmptyMonad.INSTANCE)
                .bind(x -> MyMonad.of(x + " for Real"));

        // it works !
        assertThat(applied).isInstanceOf(EmptyMonad.class);
        assertThat(applied).isEqualTo(EmptyMonad.INSTANCE);
    }

}
