package ru.otus.shatokhin.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class User {

    private String firstName;

    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
