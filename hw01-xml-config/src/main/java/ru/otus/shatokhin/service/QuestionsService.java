package ru.otus.shatokhin.service;

import ru.otus.shatokhin.model.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionsService {

    List<Question> getQuestions() throws IOException;
}
