package com.ivan.garcia.volleytest.Objects;

public class User {
    private String id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Address address;
    private Company company;

    class Address {
        private String street;
        private String suit;
        private String city;
        private String zipcode;
        private Geo geo;
    }

    class Geo {
        private String lat;
        private String lng;
    }

    class Company {
        private String catchPhrase;
        private String name;
        private String bs;
    }

    public String toString() {
        return id + " - " + name + "\n" + address.street + ", " + address.city + "\n" + company.name + "\n" + address.geo.lat + " , " + address.geo.lng;
    }

}
