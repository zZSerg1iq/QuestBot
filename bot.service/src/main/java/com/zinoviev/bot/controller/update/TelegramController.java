package com.zinoviev.bot.controller.update;

import com.zinoviev.bot.entity.rest.UpdateData;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramController {

    public void saveUpdatedInfo(UpdateData updateData);

    //void sendMessageList(LinkedHashMap<Object, Integer> messageList);
    void sendMessage(SendMessage message);
    void sendMessage(EditMessageText editMessageText);
    void sendMessage(EditMessageReplyMarkup replyMarkup);
    void sendMessage(DeleteMessage deleteMessage);
    void sendMessage(SendAnimation sendAnimation);
    void sendMessage(SendPhoto sendPhoto);
    void sendMessage(SendMediaGroup sendMediaGroup);
    void downloadQuestFile(Update update);
}
