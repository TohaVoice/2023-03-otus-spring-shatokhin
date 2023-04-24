package ru.otus.shatokhin.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class QuizMatcher {

    private long numberOfQuestion;

    private Character correctAnswerLetter;
}
