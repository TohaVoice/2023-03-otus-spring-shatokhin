package ru.otus.shatokhin.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Author {

    private long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    public Author(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
