package ru.otus.shatokhin.service.quiz.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.shatokhin.model.User;
import ru.otus.shatokhin.service.UserService;
import ru.otus.shatokhin.service.localization.LocalisationMessageService;
import ru.otus.shatokhin.service.quiz.QuizCommand;
import ru.otus.shatokhin.service.quiz.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class ShellQuizCommand implements QuizCommand {

    private final QuizService quizService;

    private final UserService userService;

    private final LocalisationMessageService msgService;

    private User user;

    @ShellMethod(value = "Login to the app", key = {"l", "login"})
    public String login() {
        user = userService.askUserInfo();
        return msgService.getMessage("msg.successLogged");
    }

    @ShellMethod(value = "Start the quiz", key = {"q", "quiz"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public void runQuiz() {
        quizService.runQuiz(user);
    }

    private Availability isQuizAvailable() {
        return user == null ? Availability.unavailable(msgService.getMessage("msg.needLogin"))
                : Availability.available();
    }

}
