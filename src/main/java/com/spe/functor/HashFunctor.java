package com.spe.functor;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.function.Function;

public class HashFunctor<T> implements Functor<T, HashFunctor> {

    private final T value;

    private HashFunctor(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> HashFunctor<T> of(T value) {
        return new HashFunctor<>(value);
    }

    public HashFunctor<String> hash() {
        String strVal = value.toString();
        return HashFunctor.of(getStringHashFunctor(strVal));
    }

    //awful static stuff
    public static String getStringHashFunctor(String strVal) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(strVal.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest);

    }

    public T get() {
        return value;
    }

    @Override
    public <B> HashFunctor<B> fmap(Function<? super T, ? extends B> fn) {
        B apply = fn.apply(value);
        return HashFunctor.of(apply);
    }

}
