package com.zinoviev.orchestrator.service.role.user_role_handlers.sub;

import com.zinoviev.bot.controller.update.TelegramController;
import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.support.DefaultBotMessages;
import com.zinoviev.bot.support.MessageTemplates;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class HelpMenuHandler {

    private final String CREATION = "HOW_TO_CREATE";
    private final String UPLOAD = "HOW_TO_UPLOAD";
    private final String EDITING = "HOW_TO_EDIT";
    private final String PLAY = "HOW_TO_PLAY";


    private final UpdateData updateData;

    private final TelegramController telegramController;

    public HelpMenuHandler(UpdateData updateData, TelegramController telegramController) {
        this.updateData = updateData;
        this.telegramController = telegramController;
    }

    public void showHelpMenu() {
        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Как создавать квесты").callbackData(CREATION).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Как добавить новый квест").callbackData(UPLOAD).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Как редактировать квесты").callbackData(EDITING).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Как принять участие в квесте").callbackData(PLAY).build());
        buttonRows.add(buttons);
        buttons = new ArrayList<>();
        buttons.add(InlineKeyboardButton.builder().text("Отмена").callbackData("ACTION_CANCEL").build());
        buttonRows.add(buttons);


        telegramController.sendMessage(
                MessageTemplates.getInlineKeyboardSendMessageTemplate(
                        updateData.getUserId(), buttonRows, DefaultBotMessages.USER_HELP_MESSAGE.getMessage()
                ));
    }

    public void helpMenuCallback() {
        switch (updateData.getCallbackQueryData()) {
            case CREATION -> {
            }
            case EDITING -> {
            }
            case UPLOAD -> {
            }
            case PLAY -> {
            }
        }
    }
}
