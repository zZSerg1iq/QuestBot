package com.zinoviev.sandbox.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class MessageTemplateService {

    public static EditMessageText getEditedMessageTemplate(CallbackQuery callbackQuery, String text) {
        return EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .text(text)
                .messageId(callbackQuery.getMessage().getMessageId())
                .build();
    }

    public static EditMessageText getEditedMessageTemplate(CallbackQuery callbackQuery, List<List<InlineKeyboardButton>> buttonRows, String text) {
        return EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttonRows)
                        .build())
                .messageId(callbackQuery.getMessage().getMessageId())
                .build();
    }


    public static SendMessage getSendMessageTemplate(long id, String text) {
        return SendMessage.builder()
                .chatId(id)
                .text(text)
                .build();
    }

    public static SendMessage getSendMessageTemplate(long id, List<List<InlineKeyboardButton>> buttonRows, String text) {
        return SendMessage.builder()
                .chatId(id)
                .replyMarkup(InlineKeyboardMarkup.builder()
                    .keyboard(buttonRows)
                    .build())
                .text(text)
                .build();
    }

}
