package ru.otus.shatokhin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.model.User;
import ru.otus.shatokhin.service.localization.LocalisationIOServiceImpl;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final LocalisationIOServiceImpl ioService;

    @Override
    public User askUserInfo() {
        String info = ioService.readStringWithLocalePrompt("msg.enterYourName");
        String[] infoArr = info.split(" ");
        if (infoArr.length != 2) {
            ioService.outputLocaleString("msg.incorrectUserData");
            return askUserInfo();
        } else {
            return new User(infoArr[0], infoArr[1]);
        }
    }
}
