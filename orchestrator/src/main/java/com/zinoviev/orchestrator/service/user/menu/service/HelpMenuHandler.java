package com.zinoviev.orchestrator.service.user.menu.service;


import com.zinoviev.entity.enums.DefaultBotMessages;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.dto.update.include.MessageDto;
import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;
import com.zinoviev.orchestrator.service.MessageBuilderService;

public class HelpMenuHandler {

    private final String CREATION = "HOW_TO_CREATE";
    private final String UPLOAD = "HOW_TO_UPLOAD";
    private final String EDITING = "HOW_TO_EDIT";
    private final String PLAY = "HOW_TO_PLAY";

    private final DataExchangeController exchangeController;
    private final MessageBuilderService messageBuilderService;

    private final UpdateDto updateDto;


    public HelpMenuHandler(DataExchangeController exchangeController, MessageBuilderService messageBuilderService, UpdateDto updateDto) {
        this.exchangeController = exchangeController;
        this.messageBuilderService = messageBuilderService;
        this.updateDto = updateDto;
    }

    public void showHelpMainMenu() {
        messageBuilderService
                .setText(updateDto, DefaultBotMessages.USER_HELP_MAIN_MENU_MESSAGE.getMessage())
                .setMessageType(updateDto, MessageType.MESSAGE)
                .setKeyboardType(updateDto, KeyboardType.INLINE)
                .setButtonsAndCallbacks(updateDto,
                        new String[]{"Создание квестов", null, "Загрузка файла квеста", null, "Редактирование квеста", null, "Как играть", null, "Отмена"},
                        new String[]{CREATION, null, UPLOAD, null, EDITING, null, PLAY, null,  "CANCEL"}
                );
        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

    public void helpMenuCallback() {
        switch (updateDto.getMessageDto().getCallbackData()) {
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
                        new String[]{"Статистика", null, "Сменить имя", null, "Отмена"},
                        new String[]{STATISTICS, null, CHANGE_NAME, null, CANCEL}
                );*/


        MessageDto messageDto = updateDto.getMessageDto();
        messageDto.setMessageType(MessageType.EDIT_MESSAGE);
        messageDto.setText(botMessages);
        messageDto.setKeyboardType(KeyboardType.NULL);
        messageDto.setButtons(null);

        exchangeController.exchangeWith(ServiceURL.botService, updateDto);
    }

}
