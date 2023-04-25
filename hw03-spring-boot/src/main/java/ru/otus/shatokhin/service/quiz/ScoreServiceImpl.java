package ru.otus.shatokhin.service.quiz;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.configs.AppProps;
import ru.otus.shatokhin.model.Answer;

import java.util.List;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final AppProps props;

    @Override
    public long calculateScore(List<Answer> answers) {
        return answers.stream()
                .filter(Answer::isCorrect)
                .count();
    }

    @Override
    public boolean isSucceedQuiz(long score) {
        return props.successScore() <= score;
    }
}
