package com.zinoviev.bot.service.role.user_role_handlers.sub;

import com.zinoviev.bot.controller.update.TelegramController;
import com.zinoviev.bot.entity.RequestStatus;
import com.zinoviev.bot.entity.SignInStatus;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.service.UserRegService;
import com.zinoviev.bot.support.DefaultBotMessages;
import com.zinoviev.bot.support.MessageTemplates;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class AccountMenuHandler {

    private final String STATISTICS = "ACCOUNT_STATISTICS";
    private final String BALANCE = "ACCOUNT_BALANCE";
    private final String CHANGE_NAME = "ACCOUNT_NAME";


    private final UpdateData updateData;

    private final TelegramController telegramController;

    private final String username;

    public AccountMenuHandler(UpdateData updateData, TelegramController telegramController) {
        this.updateData = updateData;
        this.telegramController = telegramController;
        username = updateData.getUserData().getAvatarName() != null ? updateData.getUserData().getAvatarName() : updateData.getUserData().getFirstName();
    }

    public void showAccountMenu() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Статистика").callbackData(STATISTICS).build());
       /* buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Баланс").callbackData(BALANCE).build());*/
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Сменить имя").callbackData(CHANGE_NAME).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("CANCEL").build());
        buttonRows.add(buttons);

        telegramController.sendMessage(
                MessageTemplates.getInlineKeyboardSendMessageTemplate(updateData.getUserId(), buttonRows,
                        username + DefaultBotMessages.USER_HALLO_MESSAGE_1.getMessage()
                ));
    }

    public void accountMenuCallback() {
        switch (updateData.getCallbackQueryData()) {
            case STATISTICS -> showStatistic();
            case BALANCE -> showBalance();
            case CHANGE_NAME -> changeName();
        }
    }

    private void showStatistic() {
        updateData.setRequestStatus(RequestStatus.GET_STATISTICS);
        telegramController.saveUpdatedInfo(updateData);
        new UserRegService(telegramController).proceedSignUp(updateData);
    }

    private void showBalance() {
        //
    }

    private void changeName() {
        updateData.getUserData().setSignInStatus(SignInStatus.SIGN_IN_OFFER);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        updateData.setCallbackQueryData("REG_ACCEPTED");
        telegramController.saveUpdatedInfo(updateData);
        new UserRegService(telegramController).proceedSignUp(updateData);
    }
}
