package ru.otus.shatokhin.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CsvQuestionsDaoTest {

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new CsvQuestionDaoImpl("data/test-questions-answers.csv");
    }

    @DisplayName("Get questions correctly")
    @Test
    void getQuestions() {
        List<Question> questions = questionDao.getQuestions();
        assertAll(
                () -> assertThat(questions).hasSize(2),
                () -> assertThat(questions.get(0).getText()).isEqualTo("The question #1?"),
                () -> assertThat(questions.get(0).getAnswers())
                        .hasSize(4)
                        .contains(new Answer("a", 'a'), atIndex(0))
                        .contains(new Answer("b", 'b'), atIndex(1))
                        .contains(new Answer("c", 'c'), atIndex(2))
                        .contains(new Answer("d", 'd'), atIndex(3)),
                () -> assertThat(questions.get(1).getText()).isEqualTo("The question #2?"),
                () -> assertThat(questions.get(1).getAnswers())
                        .hasSize(4)
                        .contains(new Answer("a2", 'a'), atIndex(0))
                        .contains(new Answer("b2", 'b'), atIndex(1))
                        .contains(new Answer("c2", 'c'), atIndex(2))
                        .contains(new Answer("d2", 'd'), atIndex(3))
        );
    }
}
