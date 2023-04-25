package ru.otus.shatokhin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.model.User;
import ru.otus.shatokhin.service.localization.LocalisationMessageService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    private final LocalisationMessageService msgService;

    @Override
    public User askUserInfo() {
        String info = ioService.readStringWithPrompt(msgService.getMessage("msg.enterYourName"));
        String[] infoArr = info.split(" ");
        if (infoArr.length != 2) {
            ioService.outputString(msgService.getMessage("msg.incorrectUserData"));
            return askUserInfo();
        } else {
            return new User(infoArr[0], infoArr[1]);
        }
    }
}
