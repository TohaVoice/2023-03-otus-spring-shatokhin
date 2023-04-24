package ru.otus.shatokhin.service.quiz;

import ru.otus.shatokhin.model.Answer;

import java.util.List;

public interface ScoreService {

    long calculateScore(List<Answer> answers);

    boolean isSucceedQuiz(long score);
}
