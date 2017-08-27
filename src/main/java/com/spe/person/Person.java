package com.spe.person;

public class Person {

    private final String name;
    private final Person.Address address;
    private final Integer age;
    private final PersonBoxed<Person> son;

    public Person(String name, Address address, Integer age, PersonBoxed<Person> son) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.son = son;
    }

    public String getName() {
        return name;
    }

    public PersonBoxed<Person> getSon() {
        return son;
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
