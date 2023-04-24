package ru.otus.shatokhin.service.quiz;

import ru.otus.shatokhin.model.QuizMatcher;

import java.util.List;

public interface ScoreService {

    int calculateScore(List<QuizMatcher> resultMatchers);

    boolean isSucceedQuiz(int score);
}
