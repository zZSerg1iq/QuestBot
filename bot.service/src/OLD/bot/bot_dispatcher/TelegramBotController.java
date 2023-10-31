package com.zinoviev.questbot.OLD.bot.bot_dispatcher;

import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;

public interface TelegramBotController {


    void sendMessageList(LinkedHashMap<Object, Integer> messageList);
    void sendMessage(SendMessage message);
    void sendMessage(EditMessageText editMessageText);
    void sendMessage(EditMessageReplyMarkup replyMarkup);
    void sendMessage(SendAnimation sendAnimation);
    void sendMessage(SendPhoto sendPhoto);
    void sendMessage(SendMediaGroup sendMediaGroup);
    void downloadQuestFile(Update update);
}
