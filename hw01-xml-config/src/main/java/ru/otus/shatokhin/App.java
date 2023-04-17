package ru.otus.shatokhin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.shatokhin.model.Question;
import ru.otus.shatokhin.service.QuestionsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionsService questionsService = context.getBean(QuestionsService.class);
        final List<Question> questions;
        try {
            questions = questionsService.getQuestions();
            System.out.print(questions.stream()
                    .map(Question::toString)
                    .collect(Collectors.joining()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to get a list of questions", e);
        }
    }
}
