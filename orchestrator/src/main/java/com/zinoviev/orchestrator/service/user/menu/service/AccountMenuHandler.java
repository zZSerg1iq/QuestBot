package com.zinoviev.orchestrator.service.user.menu.service;

import com.zinoviev.entity.enums.*;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;
import com.zinoviev.orchestrator.service.MessageBuilderService;
import com.zinoviev.orchestrator.service.SignUpService;

public class AccountMenuHandler {

    private final String STATISTICS = "ACCOUNT_STATISTICS";
    private final String CHANGE_NAME = "ACCOUNT_NAME";

    private final DataExchangeController exchangeController;
    private final UpdateDto updateDto;
    private final MessageBuilderService messageBuilderService;

    public AccountMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateDto updateDto) {
        this.exchangeController = exchangeController;
        this.updateDto = updateDto;
        this.messageBuilderService = messageBuilderService;
    }

    public void showAccountMainMenu() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Статистика", null, "Сменить имя", null, "Отмена"},
                        new String[]{STATISTICS, null, CHANGE_NAME, null, "CANCEL"}
                );
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    public void accountMenuCallback() {
        switch (updateDto.getMessageDto().getCallbackData()) {
            case STATISTICS -> showStatistic();
            case CHANGE_NAME -> changeName();
        }
    }

    private void showStatistic() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateDto, MessageType.EDIT_MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.NULL);
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    private void changeName() {
        updateDto.getUserDTO().setSignInStatus(SignInStatus.SIGN_UP_OFFER);
        updateDto.setRequestType(RequestType.SAVE_ONLY);
        updateDto.getMessageDto().setCallbackData("REG_ACCEPTED");
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);

        new SignUpService(exchangeController).proceedSignUp(updateDto);
    }
}
