package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.shatokhin.configs.AppProps;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionsDaoTest {

    @Mock
    private AppProps propsMock;

    @InjectMocks
    private CsvQuestionDao csvQuestionDao;

    @DisplayName("Get questions correctly")
    @Test
    void getQuestions() {
        when(propsMock.questionsPath()).thenReturn("data/test-questions-answers.csv");
        List<Question> questions = csvQuestionDao.getQuestions();
        assertAll(
                () -> assertThat(questions).hasSize(2),
                () -> assertThat(questions.get(0).getText()).isEqualTo("The question #1?"),
                () -> assertThat(questions.get(0).getAnswers())
                        .hasSize(4)
                        .contains(new Answer("a", 'a', true), atIndex(0))
                        .contains(new Answer("b", 'b', false), atIndex(1))
                        .contains(new Answer("c", 'c', false), atIndex(2))
                        .contains(new Answer("d", 'd', false), atIndex(3)),
                () -> assertThat(questions.get(1).getText()).isEqualTo("The question #2?"),
                () -> assertThat(questions.get(1).getAnswers())
                        .hasSize(4)
                        .contains(new Answer("a2", 'a', false), atIndex(0))
                        .contains(new Answer("b2", 'b', true), atIndex(1))
                        .contains(new Answer("c2", 'c', false), atIndex(2))
                        .contains(new Answer("d2", 'd', false), atIndex(3))
        );
    }
}
