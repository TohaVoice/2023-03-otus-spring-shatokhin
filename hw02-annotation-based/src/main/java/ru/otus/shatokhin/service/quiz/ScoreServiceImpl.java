package ru.otus.shatokhin.service.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.model.Answer;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final int successScore;

    public ScoreServiceImpl(@Value("${quiz.successScore}") int successScore) {
        this.successScore = successScore;
    }

    @Override
    public long calculateScore(List<Answer> answers) {
        return answers.stream()
                .filter(Answer::isCorrect)
                .count();
    }

    @Override
    public boolean isSucceedQuiz(long score) {
        return successScore <= score;
    }
}
