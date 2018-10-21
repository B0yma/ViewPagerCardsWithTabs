package com.boyma.viewpagercards.models;

import java.io.Serializable;

public class Person implements Serializable {

    public String getName() {
        return name;
    }

    private String name;
    private String surname;
    private String date;

    public Person(String name, String surname, String date) {
        this.name = name;
        this.surname = surname;
        this.date = date;
    }



}
