package com.spe;

import com.spe.maybe.Just;
import com.spe.maybe.Maybe;
import com.spe.maybe.Nothing;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class Main {

    public Maybe<Person> find(String name) {
        Person jean = new Person("jean", new Nothing<>());
        Person wesh = new Person("wesh", new Nothing<>());
        Person bon = new Person("bon", new Just<>(wesh));
        Person frank = new Person("frank", new Just<>(jean));
        Person kimek = new Person("frank", new Just<>(bon));

        return new Just(frank);
    }

    class Person {
        public final String name;

        public final Maybe<Person> child;

        public Person(String name, Maybe<Person> child) {
            this.name = name;
            this.child = child;
        }
    }

    class Streum {
        public final String name;

        public final Maybe<Streum> child;

        public Streum(String name, Maybe<Streum> child) {
            this.name = name;
            this.child = child;
        }
    }

    //--- functors vs monads


/*
    Maybe<Integer> maybe = new Just(1);
    find(maybe).
            maybe.map(x -> x.toString());
    Maybe<String> bind = maybe.bind(x -> new Just(x.toString())); // je sais que c un just
    //que faire quand on ne sait pas ?? ou alors essayer avec un maybe de maybe ?
    Nothing<Integer> nuttin = new Nothing<>();
        nuttin.map(x -> x.toString());
        nuttin.bind(x -> new Just(x.toString()));*/


    public void monad() {
        Maybe<Person> person = find("jean");

        // dou linteret du flatmap ???
        boolean hasSon = person.bind(x -> x.child).isJust();
        boolean hasGrandSon = person.bind(x -> x.child).bind(x -> x.child).isJust();
        boolean hasGrandGrandSon = person.bind(x -> x.child).bind(x -> x.child).bind(x -> x.child).isJust();

        Person grandSon = person.bind(x -> x.child).bind(x -> x.child).fromJust();

        //
        String name = person.bind(x -> x.child).bind(x -> x.child).fromJust().name;
        // ne devrait pas marcher. runtime exception ?
        Maybe<Person> addFamilyName = ((Maybe<Person>) person.fmap(x -> new Person(x.name + " Petit", x.child)));


    }




    public static void main(String[] args) {

        Optional<Integer> lol = Optional.of(12);

        Optional<String> s1 = lol.flatMap(x -> Optional.of(x + ""));
        Optional<String> s = lol.map(x -> x + "");
        // same as map
        Optional<String> s2 = lol.flatMap(x -> Optional.of(x + ""));


        System.out.println(Stream.of(1, 2, 3).collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3).map(x -> x + "str").collect(Collectors.toList()));

        System.out.println(Stream.of(1, 2, 3).flatMap(x -> Stream.of(x + "str")).collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3).flatMap(x -> Stream.of(x + 1)).collect(Collectors.toList()));
        System.out.println(Stream.of(1, 2, 3).flatMap(x -> Stream.of(new BigDecimal(x))).collect(Collectors.toList()));
        System.out.println("lol");
        System.out.println(Stream.of(asList(1, 2), asList(3, 4)).flatMap(x -> x.stream()).collect(Collectors.toList()));
        System.out.println(Stream.of(asList(1, 2), asList(3, 4)).map(x -> x.stream()).collect(Collectors.toList()));
        //  m map g = flatMap(x => unit(g(x))) -> same as map
        System.out.println(Stream.of(asList(1, 2), asList(3, 4)).flatMap(x -> Stream.of(x.stream())).collect(Collectors.toList()));
        System.out.println("k");

        System.out.println(Stream.of(asList(1, 2), asList(3, 4)).map(x -> new HashSet(x)).collect(Collectors.toList()));
        //  m map g = flatMap(x => unit(g(x))) -> same as map
        System.out.println(Stream.of(asList(1, 2), asList(3, 4)).flatMap(x -> Stream.of(new HashSet(x))).collect(Collectors.toList()));
        // List(1,2) List(3,4) --map -> Stream(Set(1,2))  Stream(Set(3,4)) -flatten-> Set(1,2) Set(3,4)

        // du coup, qu'est ce que flatmap permet de plus ???
        // flatmap est plus puissant que map, map est un sous ensemble de flatmap
        // flatmap pour les monades, map pour les functors
        // a creuser, mais cela est utile lorsque la fonction renvoie autre chose qu'un simple mapping de valeur, genre nothing / empty

        System.out.println(Stream.of(Stream.of(1, 2), Stream.of(3, 4)).flatMap(Function.identity()).collect(Collectors.toList()));
        System.out.println(Stream.of(asList(1, 2), Stream.of(3, 4)).flatMap(x -> Stream.of(x)).collect(Collectors.toList()));


        List<BigDecimal> collect1 = Stream.of(1, 2, 3).flatMap(x -> Stream.of(new BigDecimal(x))).collect(Collectors.toList());

        List<String> integers = asList("a", "b", "c");
        List<String> integers2 = asList("d", "e");


        List<List<String>> integersMeta = asList(integers, integers2);
        List<List<String>> collect2 = integersMeta.stream().collect(Collectors.toList());
        List<String> collect = integersMeta.stream().flatMap(x -> Stream.of(x + "lol")).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(collect2);


    }
}
