package com.zinoviev.orchestrator.service.user.sub;

import com.zinoviev.entity.enums.*;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;
import com.zinoviev.orchestrator.service.MessageBuilderService;
import com.zinoviev.orchestrator.service.SignUpService;

public class AccountMenuHandler {

    private final String STATISTICS = "ACCOUNT_STATISTICS";
    private final String CHANGE_NAME = "ACCOUNT_NAME";

    private final DataExchangeController exchangeController;
    private final UpdateData updateData;
    private final MessageBuilderService messageBuilderService;

    public AccountMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateData updateData) {
        this.exchangeController = exchangeController;
        this.updateData = updateData;
        this.messageBuilderService = messageBuilderService;
    }

    public void showAccountMainMenu() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Статистика", null, "Сменить имя", null, "Отмена"},
                        new String[]{STATISTICS, null, CHANGE_NAME, null, "CANCEL"}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    public void accountMenuCallback() {
        switch (updateData.getMessage().getCallbackData()) {
            case STATISTICS -> showStatistic();
            case CHANGE_NAME -> changeName();
        }
    }

    private void showStatistic() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateData, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateData, KeyboardType.NULL);
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    private void changeName() {
        updateData.getUserData().setSignInStatus(SignInStatus.SIGN_UP_OFFER);
        updateData.setRequestStatus(RequestStatus.SAVE_ONLY);
        updateData.getMessage().setCallbackData("REG_ACCEPTED");
        exchangeController.sendDataTo(ServiceNames.DATA_SERVICE, updateData);

        new SignUpService(exchangeController).proceedSignUp(updateData);
    }
}
