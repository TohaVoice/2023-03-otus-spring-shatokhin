package ru.otus.shatokhin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.shatokhin.service.QuestionService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionsService = context.getBean(QuestionService.class);
        questionsService.printQuestions();
    }
}
