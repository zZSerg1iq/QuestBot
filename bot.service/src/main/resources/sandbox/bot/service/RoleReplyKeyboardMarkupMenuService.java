package com.zinoviev.sandbox.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class RoleReplyKeyboardMarkupMenuService {

    public static SendMessage getUserReplyKeyboardMarkupMenu(long id, String messageText) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Меню");
        keyboardRow.add("Справка");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше

        return SendMessage.builder()
                        .chatId(id)
                        .text(messageText)
                        .replyMarkup(keyboardMarkup)
                        .build();
    }

    public static SendMessage getAdminReplyKeyboardMarkupMenu(long id, String messageText) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Квест");
        keyboardRow.add("Игроки");
        keyboardRow.add("Справка");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше

        return SendMessage.builder()
                .chatId(id)
                .text(messageText)
                .replyMarkup(keyboardMarkup)
                .build();
    }


    public static SendMessage getPlayerReplyKeyboardMarkupMenu(long id, String messageText) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Справка");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше

        return SendMessage.builder()
                .chatId(id)
                .text(messageText)
                .replyMarkup(keyboardMarkup)
                .build();
    }


    public static SendMessage removeKeyboard(long id, String text) {
        return SendMessage.builder()
                .chatId(id)
                .text(text)
                .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                .build();
    }
}
