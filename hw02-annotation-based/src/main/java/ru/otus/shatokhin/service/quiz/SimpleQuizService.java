package ru.otus.shatokhin.service.quiz;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;
import ru.otus.shatokhin.service.IOService;
import ru.otus.shatokhin.service.QuestionConverter;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleQuizService implements QuizService {

    private final QuestionDao questionDao;

    private final IOService ioService;

    private final QuestionConverter questionConverter;

    private final ScoreService scoreService;

    @Override
    public void runQuiz() {
        String fullName = ioService.readStringWithPrompt("Enter your first and last name:");
        List<Question> questions = questionDao.getQuestions();
        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            Answer answer = runQuestion(question);
            answers.add(answer);
        }

        long score = scoreService.calculateScore(answers);

        printResult(fullName, score, questions.size());
    }

    private void printResult(String fullName, long score, int questionsCount) {
        ioService.outputString(String.format("Number of correct answers %s out of %s.", score, questionsCount));
        if (scoreService.isSucceedQuiz(score)) {
            ioService.outputString(String.format("%s, you have passed the examination", fullName));
        } else {
            ioService.outputString(String.format("%s, you did not pass the exam", fullName));
        }
    }

    private Character convertAnswerToLetter(String answer) {
        answer = answer.toLowerCase().trim().stripTrailing();
        return answer.length() != 1 ? null : answer.charAt(0);
    }

    private Answer getAnswerByInput(String input, List<Answer> questionAnswers) {
        Character answerLetter = convertAnswerToLetter(input);
        return questionAnswers.stream()
                .filter(a -> a.getLetter().equals(answerLetter))
                .findFirst()
                .orElse(null);
    }

    private Answer runQuestion(Question question) {
        String answer = ioService.readStringWithPrompt(questionConverter.questionToString(question));
        Answer answerByInput = getAnswerByInput(answer, question.getAnswers());
        if (answerByInput == null) {
            ioService.outputString("Sorry, but your answer is incorrect, try again\n");
            return runQuestion(question);
        } else {
            return answerByInput;
        }
    }

}
