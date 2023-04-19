package ru.otus.shatokhin.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.shatokhin.exception.CsvReadException;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvQuestionDaoImpl implements QuestionDao {

    private final String fileName;

    public CsvQuestionDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    private Resource getQuestionsResource() {
        return new ClassPathResource(fileName);
    }

    private String[] getHeaders() {
        return new String[]{"question", "a_answer", "b_answer", "c_answer", "d_answer"};
    }

    private Question convertRecordToQuestion(CSVRecord record) {
        String[] headers = getHeaders();
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i < headers.length; i++) {
            String answerHeader = headers[i];
            answers.add(new Answer(record.get(answerHeader), answerHeader.charAt(0)));
        }
        return new Question(record.getRecordNumber(), record.get(headers[0]), answers);
    }

    public List<Question> getQuestions() {
        Resource questionsResource = getQuestionsResource();
        List<Question> questions = new ArrayList<>();
        try (InputStream csvStream = questionsResource.getInputStream();
             Reader in = new InputStreamReader(csvStream)) {
            String[] headers = getHeaders();
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(headers).parse(in);
            for (CSVRecord record : records) {
                questions.add(convertRecordToQuestion(record));
            }
            return questions;
        } catch (IOException | IllegalArgumentException e) {
            throw new CsvReadException("Unable to read a list of questions from .csv file", e);
        }
    }
}
