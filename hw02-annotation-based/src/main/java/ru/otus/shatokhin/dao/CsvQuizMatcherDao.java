package ru.otus.shatokhin.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.shatokhin.exception.QuizMatcherReadException;
import ru.otus.shatokhin.model.QuizMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvQuizMatcherDao implements QuizMatcherDao {

    private final String fileName;

    public CsvQuizMatcherDao(@Value("${quiz.matchersPath}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<QuizMatcher> getQuizMatchers() {
        Resource questionsResource = getQuizConfigResource();
        List<QuizMatcher> quizMatchers = new ArrayList<>();
        try (InputStream csvStream = questionsResource.getInputStream();
             Reader in = new InputStreamReader(csvStream)) {
            String[] headers = getHeaders();
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(headers).parse(in);
            for (CSVRecord record : records) {
                quizMatchers.add(convertRecordToQuizMatcher(record));
            }
            return quizMatchers;
        } catch (IOException | IllegalArgumentException e) {
            throw new QuizMatcherReadException("Unable to read a list of quiz matchers from .csv file", e);
        }
    }

    private Resource getQuizConfigResource() {
        return new ClassPathResource(fileName);
    }

    private String[] getHeaders() {
        return new String[]{"numberOfQuestion", "correctAnswerLetter"};
    }

    private QuizMatcher convertRecordToQuizMatcher(CSVRecord record) {
        String[] headers = getHeaders();
        String numberOfQuestionHeader = headers[0];
        String correctAnswerLetterHeader = headers[1];
        long numberOfQuestion = Long.parseLong(record.get(numberOfQuestionHeader));
        char correctAnswerLetter = record.get(correctAnswerLetterHeader).charAt(0);
        return new QuizMatcher(numberOfQuestion, correctAnswerLetter);
    }

}
