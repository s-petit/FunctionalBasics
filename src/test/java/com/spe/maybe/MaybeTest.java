package com.spe.maybe;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MaybeTest {

    private final Person fils = new Person("fiston", new Nothing<>());
    private final Person pere = new Person("papa", new Just<>(fils));
    private final Person grandPere = new Person("papi", new Just<>(pere));

    private List<Person> people = asList(grandPere, pere, fils);

    @Before
    public void setUp() throws Exception {

    }

   /* @Test
    public void lol() {

        Maybe<Person> person = find("kimek");

        // dou linteret du flatmap ???
        Maybe<Person> son = person.bind(x -> x.child);
        boolean hasSon = son.isJust();
        String sonName = son.fromJust().name;
        Maybe<Person> grandSon = son.bind(x -> x.child);
        boolean hasGrandSon = grandSon.isJust();
        String grandSonName = grandSon.fromJust().name;
        Maybe<Person> grandGrandSon = grandSon.bind(x -> x.child);
        boolean hasGrandGrandSon = grandGrandSon.isJust();
//        String grandGrandSonName = grandGrandSon.fromJust().name;

        assertThat(hasSon).isTrue();
        assertThat(sonName).isEqualTo("bon");
        assertThat(hasGrandSon).isTrue();
        assertThat(grandSonName).isEqualTo("wesh");
        assertThat(hasGrandGrandSon).isFalse();

    }*/

    @Test
    public void functor() {
        Maybe<Person> person = find("papi");
        Maybe<Streum> streum = ((Maybe<Streum>) person.fmap(x -> new Streum(x.name)));

        assertThat(streum.isJust()).isTrue();
        assertThat(streum.fromJust().name).isEqualTo("papi");

    }

    @Test
    public void monad() {
        Maybe<Person> person = find("papi");
        Maybe<Person> grandSon = person.bind(x -> x.firstChild).bind(x -> x.firstChild);

        assertThat(grandSon.isJust()).isTrue();
        assertThat(grandSon.fromJust().name).isEqualTo("fiston");

        Maybe<Person> grandGrandSon = person.bind(x -> x.firstChild).bind(x -> x.firstChild).bind(x -> x.firstChild);
        assertThat(grandGrandSon.isJust()).isFalse();

    }


    public Maybe<Person> find(String name) {
        Optional<Person> person = people.stream().filter(x -> x.name.equals(name)).findFirst();

        if (person.isPresent()) {
            return new Just<>(person.get());
        }

        return new Nothing<>();

    }

    class Person {
        public final String name;
        public final Maybe<Person> firstChild;


        public Person(String name, Maybe<Person> firstChild) {
            this.name = name;
            this.firstChild = firstChild;
        }
    }

    class Streum {
        public final String name;

        public Streum(String name) {
            this.name = name;
        }
    }

}