package com.spe;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class StreamTest {

    //TODO more explicit test, and replace sout with assertThat
    @Test
    public void playing_with_streams_and_flatmap() {
        //flatmap is the monadic bind for java streams

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

        System.out.println(Stream.of(Stream.of(1, 2), Stream.of(3, 4)).flatMap(Function.identity()).collect(Collectors.toList()));
        System.out.println(Stream.of(asList(1, 2), Stream.of(3, 4)).flatMap(x -> Stream.of(x)).collect(Collectors.toList()));


        List<String> integers = asList("a", "b", "c");
        List<String> integers2 = asList("d", "e");

        List<List<String>> integersMeta = asList(integers, integers2);
        List<List<String>> collect2 = integersMeta.stream().collect(Collectors.toList());
        List<String> collect = integersMeta.stream().flatMap(x -> Stream.of(x + "lol")).collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(collect2);
    }
}
