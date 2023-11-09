package com.zinoviev.bot.service;

import com.zinoviev.bot.controller.TelegramController;
import com.zinoviev.bot.entity.RequestStatus;
import com.zinoviev.bot.entity.SignInStatus;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.support.DefaultBotMessages;
import com.zinoviev.bot.support.RoleReplyKeyboardMarkupMenuTemplates;
import com.zinoviev.entity.enums.RequestStatus;
import com.zinoviev.entity.enums.SignInStatus;
import com.zinoviev.entity.model.UpdateData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class UserRegService {

    private final TelegramController telegramController;

    public UserRegService(TelegramController telegramController) {
        this.telegramController = telegramController;
    }

    public void proceedSignUp(UpdateData updateData) {
        selectSignUpStage(updateData);
    }

    private void selectSignUpStage(UpdateData updateData) {
        System.out.println(updateData.getUserData().getSignInStatus());
        switch (updateData.getUserData().getSignInStatus()) {
            case SIGN_IN_NONE -> userSignUpShowOffer(updateData);
            case SIGN_IN_OFFER -> userSignUpSelectNameType(updateData);
            case SIGN_IN_SELECT_NAME_TYPE -> userSignNameTypeCallbackHandler(updateData);
            case SIGN_IN_SELECT_NAME -> userSignUpAcceptNewName(updateData);
        }
    }


    private void userSignUpShowOffer(UpdateData updateData) {
        updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_OFFER);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        telegramController.saveUpdatedInfo(updateData);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Да, конечно").callbackData("REG_ACCEPTED").build());
        buttons.add(InlineKeyboardButton.builder().text("Нет, спасибо").callbackData("REG_DENY").build());
        buttonRows.add(buttons);
        String message = DefaultBotMessages.SIGN_UP_OFFER.getMessage();

        telegramController.sendMessage(SendMessage.builder()
                .chatId(updateData.getUserId())
                .text(message)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttonRows)
                        .build())
                .build());
    }

    private void userSignUpSelectNameType(UpdateData updateData) {
        if (updateData.getCallbackQueryData() != null) {

            if (updateData.getCallbackQueryData().equals("REG_ACCEPTED")) {
                System.out.println(updateData);

                updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_SELECT_NAME_TYPE);
                updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
                telegramController.saveUpdatedInfo(updateData);

                List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
                List<InlineKeyboardButton> buttons = new ArrayList<>();
                buttons.add(InlineKeyboardButton.builder().text("Я его придумаю").callbackData("REG_NEW_NAME").build());
                buttons.add(InlineKeyboardButton.builder().text(updateData.getFirstName()).callbackData("REG_USER_NAME").build());
                buttons.add(InlineKeyboardButton.builder().text("Нет, спасибо").callbackData("REG_DENY").build());
                buttonRows.add(buttons);
                String message = DefaultBotMessages.SIGN_UP_SELECT_NAME_TYPE.getMessage();

                telegramController.sendMessage(EditMessageText.builder()
                        .chatId(updateData.getUserId())
                        .text(message)
                        .messageId(updateData.getCallbackQueryMessageId())
                        .replyMarkup(InlineKeyboardMarkup.builder()
                                .keyboard(buttonRows)
                                .build())
                        .build());
            } else {
                System.out.println("CANCEL");
                regCancel(updateData);
            }
        }
    }


    private void userSignNameTypeCallbackHandler(UpdateData updateData) {

        if (updateData.getCallbackQueryData() != null) {
            String message;
            updateData.setRequestStatus(RequestStatus.SAVE_ONLY);

            if (updateData.getCallbackQueryData().equals("REG_NEW_NAME")) {
                updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_SELECT_NAME);
                telegramController.saveUpdatedInfo(updateData);
                message = DefaultBotMessages.SIGN_UP_SELECT_NAME.getMessage();

            } else if (updateData.getCallbackQueryData().equals("REG_USER_NAME")) {
                updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_COMPLETE);
                updateData.getUserData().setAvatarName(null);
                telegramController.saveUpdatedInfo(updateData);

                message = DefaultBotMessages.SIGN_UP_COMPLETE.getMessage();
            } else {
                regCancel(updateData);
                return;
            }

            telegramController.sendMessage(EditMessageText.builder()
                    .chatId(updateData.getUserId())
                    .text(message)
                    .messageId(updateData.getCallbackQueryMessageId())
                    .build());

            if (updateData.getUserData().getSignInStatus() == SignInStatus.SIGN_IN_COMPLETE) {
                telegramController.sendMessage(
                        RoleReplyKeyboardMarkupMenuTemplates.getUserReplyKeyboardMarkupMenuMessage(
                                updateData.getChatId(), "*Вам добавлена пользовательская панель управления*"));
            }
        } else {
            userSignUpAcceptNewName(updateData);
        }

    }


    private void userSignUpAcceptNewName(UpdateData updateData) {
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_COMPLETE);
        updateData.getUserData().setAvatarName(updateData.getText());
        telegramController.saveUpdatedInfo(updateData);

        String message = DefaultBotMessages.SIGN_UP_COMPLETE.getMessage();

        telegramController.sendMessage(SendMessage.builder()
                .chatId(updateData.getUserId())
                .text(message)
                .replyMarkup(RoleReplyKeyboardMarkupMenuTemplates.getUserReplyKeyboardMarkupMenu())
                .build());
    }


    private void regCancel(UpdateData updateData) {
        updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_NONE);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        telegramController.saveUpdatedInfo(updateData);

        if (updateData.getCallbackQueryData() != null) {
            telegramController.sendMessage(EditMessageText.builder()
                    .chatId(updateData.getUserId())
                    .text(DefaultBotMessages.SIGN_UP_DENY.getMessage())
                    .messageId(updateData.getCallbackQueryMessageId())
                    .replyMarkup(null)
                    .build());
        } else {
            telegramController.sendMessage(SendMessage.builder()
                    .chatId(updateData.getUserId())
                    .text(DefaultBotMessages.SIGN_UP_DENY.getMessage())
                    .build());
        }
    }

}
