package ru.otus.shatokhin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;
import ru.otus.shatokhin.service.quiz.QuizService;
import ru.otus.shatokhin.service.quiz.ScoreService;
import ru.otus.shatokhin.service.quiz.SimpleQuizService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleQuizServiceTest {

    @Mock
    private QuestionDao questionDaoMock;

    @Mock
    private IOService ioServiceMock;

    @Mock
    private QuestionConverter questionConverterMock;

    @Mock
    private ScoreService scoreServiceMock;

    @InjectMocks
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
        when(ioServiceMock.readStringWithPrompt(any()))
                .thenReturn("Test User")
                .thenReturn("a")
                .thenReturn("b");

        quizService.runQuiz();

        verify(ioServiceMock, times(3)).readStringWithPrompt(any());
        verify(questionConverterMock, times(2)).questionToString(any());
        verify(ioServiceMock, times(2)).outputString(any());
        verify(scoreServiceMock, times(1)).calculateScore(any());

    }

}
