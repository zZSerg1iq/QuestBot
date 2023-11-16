package com.zinoviev.sandbox.data.service.impl;

import com.zinoviev.sandbox.bot.entity.*;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.data.entity.user.*;
import com.zinoviev.sandbox.data.repository.UserRepository;
import com.zinoviev.sandbox.data.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BotUser getBotUserById(long id) {
        return new BotUser(userRepository.findById(id).orElseGet(User::new));
    }

    @Override
    public BotUser getBotUserByTelegramId(long id) {
        User dataUser = userRepository.getUserByTelegramId(id);
        BotUser botUser;

        if (dataUser == null) {
            botUser = new BotUser();
            botUser.setRole(Role.USER);
            botUser.setUserStatus(SignInStatus.SIGN_IN_STARTED);
        } else {
            botUser = new BotUser(dataUser);
        }
        return botUser;
    }

    @Override
    public boolean isNamePresent(String name) {
        return userRepository.getUserByAvatarName(name) != null;
    }



    @Override
    public void saveUser(BotUser botUser) {
        User user = userRepository.findById(botUser.getId()).orElseGet(User::new);

        if (botUser.getId() == 0 && botUser.getBotUserAccount() == null) {
            user.setTelegramId(botUser.getTelegramId());
            user.setTelegramName(botUser.getTelegramName());
            user.setAvatarName(botUser.getAvatarName());

            UserAccount account = new UserAccount(user, true, 0);
            user.setUserAccount(account);

            UserRole role = new UserRole(user, Role.USER);
            user.setUserRole(role);

            UserSignInStatus signInStatus = new UserSignInStatus(user, botUser.getUserStatus());
            user.setUserSignInStatus(signInStatus);

            UserPoints points = new UserPoints(user, 0);
            user.setUserPoints(points);

        } else {
            if (user.getAvatarName() == null) {
                user.setAvatarName(botUser.getAvatarName());
            }
            user.getUserAccount().setActive(botUser.getBotUserAccount().isActive());
            user.getUserAccount().setBalance(botUser.getBotUserAccount().getBalance());
            user.getUserRole().setRole(botUser.getRole());
            user.getUserSignInStatus().setStatus(botUser.getUserStatus());
            user.getUserPoints().setPoints(botUser.getUserPoints());
            user.setQuestPublicLink(botUser.getQuestPublicLink());
        }

        userRepository.save(user);
    }

}
