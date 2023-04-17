package ru.otus.shatokhin.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionsServiceImpl implements QuestionsService {

    private final String fileName;

    public QuestionsServiceImpl(String fileName) {
        this.fileName = fileName;
    }

    public Resource getQuestionsResource() {
        return new ClassPathResource(fileName);
    }

    public List<Question> getQuestions() throws IOException {
        Resource questionsResource = getQuestionsResource();
        InputStream csvStream = questionsResource.getInputStream();
        Reader in = new InputStreamReader(csvStream);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(
                new String[]{"question", "a", "b", "c", "d"})
                .parse(in);
        List<Question> questions = new ArrayList<>();
        for (CSVRecord record : records) {
            Question question = new Question(record.getRecordNumber(), record.get("question"),
                    Arrays.asList(
                            new Answer(record.get("a"), 'a')
                            , new Answer(record.get("b"), 'b')
                            , new Answer(record.get("c"), 'c')
                            , new Answer(record.get("d"), 'd')
                    ));
            questions.add(question);
        }
        return questions;
    }
}
