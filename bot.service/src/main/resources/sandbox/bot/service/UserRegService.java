package com.zinoviev.sandbox.bot.service;

import com.zinoviev.sandbox.bot.entity.DefaultBotMessages;
import com.zinoviev.sandbox.bot.bot_dispatcher.TelegramBotController;
import com.zinoviev.sandbox.bot.entity.models.user.BotUser;
import com.zinoviev.sandbox.bot.entity.SignInStatus;
import com.zinoviev.sandbox.data.service.UserRepositoryService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


public class UserRegService {

    private final Update update;
    private final UserRepositoryService userService;
    private final BotUser botUser;

    private final TelegramBotController botController;

    public UserRegService(Update update, BotUser botUser, UserRepositoryService userService, TelegramBotController botController) {
        this.update = update;
        this.userService = userService;
        this.botUser = botUser;
        this.botController = botController;
        //result = new LinkedHashMap<>();
    }

    public void proceedSignUp() {
        if (botUser.getUserStatus() == SignInStatus.SIGN_IN_STARTED & update.hasCallbackQuery()) {
            botController.sendMessage(MessageTemplateService.getEditedMessageTemplate(update.getCallbackQuery(), DefaultBotMessages.CALLBACK_ERROR_MESSAGE.getMessage()));
            return;
        }

        SignInStatus status = botUser.getUserStatus();

        if (botUser.getTelegramId() == 0L) {
            botUser.setTelegramId(update.getMessage().getChatId());
            botUser.setTelegramName(update.getMessage().getFrom().getFirstName());
        }

        switch (status) {
            case SIGN_IN_OFFER -> userSignUpEnterNewName();
            case SIGN_IN_SELECT_NAME -> userSignUpCheckName();
            default -> userSignUpShowOffer();
        }
    }

    private void userSignUpShowOffer() {
        botUser.setUserStatus(SignInStatus.SIGN_IN_OFFER);
        userService.saveUser(botUser);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Да, конечно").callbackData("REG_START").build());
        buttons.add(InlineKeyboardButton.builder().text("Нет, спасибо").callbackData("REG_DENY").build());
        buttonRows.add(buttons);

        String message;
        if (botUser.getTelegramName() == null){
            message = DefaultBotMessages.SIGN_UP_OFFER_NO_TG_NAME.getMessage();
        } else {
            message = DefaultBotMessages.SIGN_UP_OFFER.getMessage();
        }

        botController.sendMessage(SendMessage.builder()
                .chatId(botUser.getTelegramId())
                .text(message)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(buttonRows)
                        .build())
                .build()
                );
    }

    private void userSignUpEnterNewName(){
        String message = ":)";

        if (update.hasCallbackQuery()) {
            String query = update.getCallbackQuery().getData();

            if (query.equals("REG_DENY")) {
               if (botUser.getTelegramName() == null){
                    botUser.setUserStatus(SignInStatus.SIGN_IN_STARTED);
                    userService.saveUser(botUser);
                    message = DefaultBotMessages.SIGN_UP_DENY.getMessage();
                } else {
                    botUser.setUserStatus(SignInStatus.SIGN_IN_COMPLETE);
                    botUser.setAvatarName(update.getCallbackQuery().getFrom().getFirstName());
                    userService.saveUser(botUser);

                    message = DefaultBotMessages.SIGN_UP_PROCESS_NAME_ACCEPTED.getMessage();
                   botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getUserReplyKeyboardMarkupMenu(update.getCallbackQuery().getMessage().getChatId(), "Что бы Вы хотели, "+update.getCallbackQuery().getFrom().getFirstName()+" ?"));
                }

            } else if (query.equals("REG_START")) {
                botUser.setUserStatus(SignInStatus.SIGN_IN_SELECT_NAME);
                userService.saveUser(botUser);
                message = DefaultBotMessages.SIGN_UP_SELECT_NAME.getMessage();
            }

            botController.sendMessage(EditMessageText.builder()
                .chatId(botUser.getTelegramId())
                .text(message)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .build());

        } else {
            userSignUpShowOffer();
        }

    }

    private void userSignUpCheckName(){
        String messageText = "Произошла какая-то ошибка";

        if (update.hasMessage()) {
            if (!userService.isNamePresent(update.getMessage().getText())){
                botUser.setUserStatus(SignInStatus.SIGN_IN_COMPLETE);
                botUser.setAvatarName(update.getMessage().getText());
                userService.saveUser(botUser);

                messageText = DefaultBotMessages.SIGN_UP_PROCESS_NAME_ACCEPTED.getMessage();
                botController.sendMessage(RoleReplyKeyboardMarkupMenuService.getUserReplyKeyboardMarkupMenu(update.getMessage().getChatId(), "Что бы Вы хотели, "+update.getMessage().getText()+"?"));
            } else {
                messageText = DefaultBotMessages.SIGN_UP_PROCESS_NAME_DENY.getMessage();
            }
        }

        botController.sendMessage(SendMessage.builder()
                .chatId(botUser.getTelegramId())
                .text(messageText)
                .build());
    }

}
