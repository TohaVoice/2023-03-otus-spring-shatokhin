package ru.otus.shatokhin.dao;

import ru.otus.shatokhin.model.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions();
}
