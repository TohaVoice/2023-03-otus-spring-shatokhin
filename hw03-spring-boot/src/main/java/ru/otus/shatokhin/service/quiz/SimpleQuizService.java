package ru.otus.shatokhin.service.quiz;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.dao.QuestionDao;
import ru.otus.shatokhin.model.Answer;
import ru.otus.shatokhin.model.Question;
import ru.otus.shatokhin.model.User;
import ru.otus.shatokhin.service.IOService;
import ru.otus.shatokhin.service.QuestionConverter;
import ru.otus.shatokhin.service.UserService;
import ru.otus.shatokhin.service.localization.LocalisationMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleQuizService implements QuizService, CommandLineRunner {

    private final QuestionDao questionDao;

    private final IOService ioService;

    private final QuestionConverter questionConverter;

    private final ScoreService scoreService;

    private final LocalisationMessageService msgService;

    private final UserService userService;

    @Override
    public void run(String... args) {
        runQuiz();
    }

    @Override
    public void runQuiz() {
        User user = userService.askUserInfo();
        List<Question> questions = questionDao.getQuestions();
        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            Answer answer = runQuestion(question);
            answers.add(answer);
        }

        long score = scoreService.calculateScore(answers);

        printResult(user.getFullName(), score, questions.size());
    }

    private void printResult(String fullName, long score, int questionsCount) {
        ioService.outputString(msgService.getMessage("msg.correctAnswers", score, questionsCount));
        if (scoreService.isSucceedQuiz(score)) {
            ioService.outputString(msgService.getMessage("msg.passedExam", fullName));
        } else {
            ioService.outputString(msgService.getMessage("msg.notPassedExam", fullName));
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
            ioService.outputString(msgService.getMessage("msg.incorrectAnswer"));
            return runQuestion(question);
        } else {
            return answerByInput;
        }
    }

}
