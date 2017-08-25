package com.spe.functor;

public class Person {

    private final String name;
    private final Person.Address address;
    private final Integer age;

    public Person(String name, Address address, Integer age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getAge() {
        return age;
    }

    public static class Address {

        private final String zip;
        private final String city;

        public Address(String zip, String city) {
            this.zip = zip;
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public String getCity() {
            return city;
        }
    }
}
