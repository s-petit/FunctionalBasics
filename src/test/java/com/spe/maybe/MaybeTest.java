package com.spe.maybe;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MaybeTest {

    private final Alive son = new Alive("fiston", new Nothing<>());
    private final Alive father = new Alive("papa", new Just<>(son));
    private final Alive grandFather = new Alive("papi", new Just<>(father));

    @Test
    public void functor() {
        Maybe<Alive> person = new Just<>(grandFather);
        Maybe<Dead> rip = person.fmap(die());

        assertThat(rip.isJust()).isTrue();
        assertThat(rip.fromJust().name).isEqualTo("papi");

    }

    private Function<Alive, Dead> die() {
        return x -> new Dead(x.name);
    }

    @Test
    public void monad() {
        Maybe<Alive> person = new Just<>(grandFather);
        Maybe<Alive> grandSon = person.bind(x -> x.firstChild).bind(x -> x.firstChild);

        assertThat(grandSon.isJust()).isTrue();
        assertThat(grandSon.fromJust()).isEqualTo(son);
        assertThat(grandSon.fromJust().name).isEqualTo("fiston");

        //can chain binds even if Just becomes Nothing
        Maybe<Alive> grandGrandSon = person.bind(x -> x.firstChild).bind(x -> x.firstChild).bind(x -> x.firstChild);
        assertThat(grandGrandSon.isJust()).isFalse();

    }

    class Alive {
        public final String name;
        public final Maybe<Alive> firstChild;


        public Alive(String name, Maybe<Alive> firstChild) {
            this.name = name;
            this.firstChild = firstChild;
        }
    }

    class Dead {
        public final String name;

        public Dead(String name) {
            this.name = name;
        }
    }

}