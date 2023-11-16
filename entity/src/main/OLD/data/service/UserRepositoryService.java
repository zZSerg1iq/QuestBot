package com.zinoviev.sandbox.data.service;


import com.zinoviev.sandbox.bot.entity.models.user.BotUser;

public interface UserRepositoryService {

    BotUser getBotUserById(long id);

    BotUser getBotUserByTelegramId(long id);

    public void saveUser(BotUser botUser);

    boolean isNamePresent(String text);
}