package ru.otus.shatokhin.service;

import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    private final IOService ioService;

    private final QuestionConverter questionConverter;

    public QuestionServiceImpl(QuestionDao questionDao, QuestionConverter questionConverter) {
        this.questionDao = questionDao;
        this.ioService = new IOServiceStreams(System.out, System.in);
        this.questionConverter = questionConverter;
    }

    public QuestionServiceImpl(QuestionDao questionDao, IOService ioService, QuestionConverter questionConverter) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.questionConverter = questionConverter;
    }

    @Override
    public void printQuestions() {
        List<Question> questions = questionDao.getQuestions();
        for (Question question : questions) {
            String questionStr = questionConverter.questionToString(question);
            ioService.outputString(questionStr);
        }
    }
}
