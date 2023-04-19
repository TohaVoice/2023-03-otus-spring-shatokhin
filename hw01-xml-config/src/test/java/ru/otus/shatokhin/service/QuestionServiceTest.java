package ru.otus.shatokhin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionDao questionDaoMock;

    @Mock
    private IOService ioServiceMock;

    @Mock
    private QuestionConverter questionConverterMock;

    @InjectMocks
    private QuestionServiceImpl questionsService;

    @Test
    void verifyPipelineOfPrintQuestions() {
        List<Question> questions = new ArrayList<>();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("True", 'a'));
        answers.add(new Answer("False", 'b'));
        Question q1 = new Question(1, "The question #1?", answers);
        answers = new ArrayList<>();
        answers.add(new Answer("Right", 'a'));
        answers.add(new Answer("Left", 'b'));
        Question q2 = new Question(1, "The question #2?", answers);
        questions.add(q1);
        questions.add(q2);
        when(questionDaoMock.getQuestions()).thenReturn(questions);

        questionsService.printQuestions();

        verify(questionConverterMock, times(2)).questionToString(any());
        verify(ioServiceMock, times(2)).outputString(any());

    }

}
