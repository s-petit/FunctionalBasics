package com.spe.functor;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HashFunctorTest {

    @Test
    public void html_functor_should_hash_value_with_string() {
        HashFunctor<Integer> text = HashFunctor.of(42);
        assertThat(text.hash().get()).isEqualTo("A1D0C6E83F027327D8461063F4AC58A6");
    }

    @Test
    public void html_functor_should_map_value() {
        HashFunctor<Integer> price = HashFunctor.of(42);

        BigDecimal vat = BigDecimal.TEN;

        HashFunctor<BigDecimal> fmap = price
                .fmap(BigDecimal::new)
                .fmap(x -> x.add(vat));

        assertThat(fmap.get()).isEqualTo(new BigDecimal("52"));
    }

    @Test
    public void html_functor_should_go_deeper_inside_objects() {
        HashFunctor<Person> personHash = HashFunctor.of(new Person("jean", new Person.Address("78120", "Rbt"), 18));
        assertThat(personHash.get().toString()).isEqualTo("Person{name='jean', address=Address{zip='78120', city='Rbt'}, age=18}");
        assertThat(personHash.hash().get()).isEqualTo("A3B840518F13A94192F285666990D825");

        String city = personHash.fmap(Person::getAddress).fmap(Person.Address::getCity).get();
        int birthday = personHash.fmap(person -> person.getAge() + 1).get();

        assertThat(birthday).isEqualTo(19);
        assertThat(city).isEqualTo("Rbt");
    }

/*    @Test
    public void html_functor_should_surround_value_with_html_markup() {
        Md5Functor<String> text = Md5Functor.of("hi");

        Md5Functor<String> div = text.surroundWithMarkup(new Md5Functor.HTMLMarkup("div"));

        assertThat(div.get()).isEqualTo("<div>hi</div>");
    }*/

    //TODO SPE merge all tests to compare without context switching



    @Test
    public void fmap_should_stuck2() {
        HashFunctor<Integer> myFunctor = HashFunctor.of(42);

        HashFunctor applied = myFunctor
                .fmap(x -> x + 13)
                .fmap(x -> HashFunctor.of(x + 10));

        // can't chain fmap/apply when apply or fmap use a function x -> Functor(x)
        assertThat(applied.get()).isNotEqualTo("<h1>65</h1> for Real");

        //does not compile ! it's a shame we need monads
/*        HtmlFunctor huh = myFunctor
                .fmap(x -> x + 13)
                .fmap(x -> HtmlFunctor.of(x + 10))
                .fmap(x -> x + 5);*/
    }

    //TODO
/*    @Test
    public void html_functor_should_stuck_on_method_which_returns_functor() {
        Md5Functor<String> text = Md5Functor.of("hi");

        Md5Functor<String> div = text.surroundWithMarkup(new Md5Functor.HTMLMarkup("div"));

        assertThat(div.get()).isEqualTo("<div>hi</div>");
    }*/


}