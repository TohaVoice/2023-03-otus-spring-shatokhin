package ru.otus.shatokhin.service.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.dao.QuizMatcherDao;
import ru.otus.shatokhin.model.QuizMatcher;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Value("${quiz.successScore}")
    private Integer successScore;

    private final QuizMatcherDao quizMatcherDao;

    public ScoreServiceImpl(QuizMatcherDao quizMatcherDao) {
        this.quizMatcherDao = quizMatcherDao;
    }

    @Override
    public int calculateScore(List<QuizMatcher> resultMatchers) {
        int score = 0;
        List<QuizMatcher> sourceMatches = quizMatcherDao.getQuizMatchers();
        for (QuizMatcher quizMatcher : sourceMatches) {
            boolean isCorrectMatch = resultMatchers.stream()
                    .anyMatch(qm -> qm.getNumberOfQuestion() == quizMatcher.getNumberOfQuestion()
                    && qm.getCorrectAnswerLetter().equals(quizMatcher.getCorrectAnswerLetter()));
            if (isCorrectMatch) {
                score++;
            }
        }
        return score;
    }

    @Override
    public boolean isSucceedQuiz(int score) {
        return successScore <= score;
    }
}
