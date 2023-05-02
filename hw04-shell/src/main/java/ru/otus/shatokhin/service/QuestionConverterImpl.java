package ru.otus.shatokhin.service;

import org.springframework.stereotype.Component;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionConverterImpl implements QuestionConverter {
    @Override
    public String questionToString(Question question) {
        long number = question.getNumber();
        String text = question.getText();
        List<Answer> answers = question.getAnswers() != null ? question.getAnswers() : Collections.emptyList();
        return String.format("%s. %s\n%s\n", number, text, answers.stream()
                .map(answer -> answer.getLetter() + ") " + answer.getText())
                .collect(Collectors.joining(";\n")));
    }
}
