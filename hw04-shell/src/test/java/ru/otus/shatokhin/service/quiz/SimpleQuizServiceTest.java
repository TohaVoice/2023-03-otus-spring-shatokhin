package ru.otus.shatokhin.service.quiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;
import ru.otus.shatokhin.model.User;
import ru.otus.shatokhin.service.QuestionConverter;
import ru.otus.shatokhin.service.UserService;
import ru.otus.shatokhin.service.localization.LocalisationIOService;
import ru.otus.shatokhin.service.localization.LocalisationMessageService;
import ru.otus.shatokhin.service.quiz.ScoreService;
import ru.otus.shatokhin.service.quiz.SimpleQuizService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class SimpleQuizServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private QuestionDao questionDaoMock;

    @MockBean
    private LocalisationIOService ioServiceMock;

    @MockBean
    private QuestionConverter questionConverterMock;

    @MockBean
    private ScoreService scoreServiceMock;

    @Autowired
    private SimpleQuizService quizService;

    @Test
    void verifyPipelineOfQuiz() {
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("True", 'a', true));
        answers.add(new Answer("False", 'b', false));
        Question q1 = new Question(1, "The question #1?", answers);
        answers = new ArrayList<>();
        answers.add(new Answer("Right", 'a', false));
        answers.add(new Answer("Left", 'b', true));
        Question q2 = new Question(1, "The question #2?", answers);
        questions.add(q1);
        questions.add(q2);
        when(questionDaoMock.getQuestions()).thenReturn(questions);
        when(ioServiceMock.readStringWithLocalePrompt(any()))
                .thenReturn("Test User")
                .thenReturn("a")
                .thenReturn("b");

        quizService.runQuiz(new User("Ivan", "Ivanov"));

        verify(ioServiceMock, times(3)).readStringWithLocalePrompt(any());
        verify(questionConverterMock, times(3)).questionToString(any());
        verify(ioServiceMock, times(1)).outputLocaleString(any());
        verify(scoreServiceMock, times(1)).calculateScore(any());
    }

}
