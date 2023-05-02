package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.shatokhin.configs.app.AppProps;
import ru.otus.shatokhin.configs.app.AppPropsImpl;
import ru.otus.shatokhin.configs.dao.DaoConfig;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CsvQuestionsDaoTest {

    @MockBean
    private DaoConfig daoConfigMock;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @DisplayName("Get questions correctly")
    @Test
    void getQuestions() {
        when(daoConfigMock.fileName()).thenReturn("data/test-questions-answers.csv");
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
