package ru.otus.shatokhin.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Question {

    private long number;

    private String point;

    private List<Answer> answers;

    @Override
    public String toString() {
        return String.format("\n%s. %s\n%s\n", number, point, answers.stream()
                .map(Answer::toString)
                .collect(Collectors.joining(";\n")));
    }
}
