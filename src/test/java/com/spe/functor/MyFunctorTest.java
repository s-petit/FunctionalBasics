package com.spe.functor;

import com.spe.person.NotExistBoxed;
import com.spe.person.Person;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class MyFunctorTest {

    @Test
    public void html_functor_should_map_value() {
        MyFunctor<Integer> price = MyFunctor.of(42);

        BigDecimal vat = BigDecimal.TEN;

        MyFunctor<BigDecimal> fmap = price
                .fmap(BigDecimal::new)
                .fmap(x -> x.add(vat));

        assertThat(fmap.get()).isEqualTo(new BigDecimal("52"));
    }

    @Test
    public void functor_should_go_deeper_inside_objects() {
        MyFunctor<Person> person = MyFunctor.of(new Person("jean", new Person.Address("78120", "Rbt"), 18, NotExistBoxed.INSTANCE));

        String city = person.fmap(Person::getAddress).fmap(Person.Address::getCity).get();
        int birthday = person.fmap(p -> p.getAge() + 1).get();

        assertThat(birthday).isEqualTo(19);
        assertThat(city).isEqualTo("Rbt");
    }

}