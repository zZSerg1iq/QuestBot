package com.zinoviev.orchestrator.service.user.sub;


import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.InlineButton;
import com.zinoviev.entity.model.updatedata.entity.Message;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;
import com.zinoviev.orchestrator.service.MessageBuilderService;

import java.util.ArrayList;
import java.util.List;

public class HelpMenuHandler {

    private final String CREATION = "HOW_TO_CREATE";
    private final String UPLOAD = "HOW_TO_UPLOAD";
    private final String EDITING = "HOW_TO_EDIT";
    private final String PLAY = "HOW_TO_PLAY";

    private final DataExchangeController exchangeController;
    private final MessageBuilderService messageBuilderService;

    private final UpdateData updateData;


    public HelpMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateData updateData) {
        this.exchangeController = exchangeController;
        this.messageBuilderService = messageBuilderService;
        this.updateData = updateData;
    }

    public void showHelpMainMenu() {
        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_HELP_MAIN_MENU_MESSAGE.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Создание квестов", ">", "Загрузка файла квеста", ">", "Редактирование квеста", ">", "Как играть", ">", "Отмена"},
                        new String[]{CREATION, ">", UPLOAD, ">", EDITING, ">", PLAY, ">",  "CANCEL"}
                );
        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

    public void helpMenuCallback() {
        switch (updateData.getMessage().getCallbackData()) {
            case CREATION -> showHelp(DefaultBotMessages.USER_HELP_QUEST_CREATION_MESSAGE.getMessage());
            case EDITING -> showHelp(DefaultBotMessages.USER_HELP_QUEST_EDITING_MESSAGE.getMessage());
            case UPLOAD -> showHelp(DefaultBotMessages.USER_HELP_QUEST_UPLOADING_MESSAGE.getMessage());
            case PLAY -> showHelp(DefaultBotMessages.USER_HELP_PLAY_QUEST_MESSAGE.getMessage());
        }
    }

    private void showHelp(String botMessages){
/*        messageBuilderService
                .setText(updateData, DefaultBotMessages.USER_QUEST_MENU.getMessage())
                .setMessageType(updateData, MessageType.MESSAGE)
                .setKeyboardType(updateData, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateData,
                        new String[]{"Статистика", ">", "Сменить имя", ">", "Отмена"},
                        new String[]{STATISTICS, ">", CHANGE_NAME, ">", CANCEL}
                );*/


        Message message = updateData.getMessage();
        message.setMessageType(MessageType.EDIT_MESSAGE);
        message.setText(botMessages);
        message.setKeyboardType(KeyboardType.NULL);
        message.setButtons(null);

        exchangeController.sendDataTo(ServiceNames.BOT_SERVICE, updateData);
    }

}
