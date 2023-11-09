package com.zinoviev.bot.message.handler;

import com.zinoviev.bot.controller.TelegramController;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.Message;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageBuilder implements ResponseMessageBuilder {

    private final TelegramController telegramController;

    public MessageBuilder(TelegramController telegramController) {
        this.telegramController = telegramController;
    }

    public void buildMessage(UpdateData updateData) {
        buildMessageBody(updateData);
    }

    private void buildMessageBody(UpdateData updateData) {
        Message message = updateData.getMessage();

        switch (message.getMessageType()) {
            case MESSAGE -> buildSendMessage(updateData);
            case EDIT_MESSAGE -> buildSendEditTextMessage(updateData);
            case DELETE -> buildDeleteMessage(updateData);
        }
    }

    private void buildSendMessage(UpdateData updateData) {
        SendMessage message = SendMessage.builder()
                .chatId(updateData.getMessage().getUserId())
                .text(updateData.getMessage().getText())
                .replyMarkup(getReplyKeyboard(updateData))
                .build();

        telegramController.sendMessage(message);
    }

    private void buildSendEditTextMessage(UpdateData updateData) {
        EditMessageText message = EditMessageText.builder()
                .chatId(updateData.getMessage().getChatId())
                .text(updateData.getMessage().getText())
                .replyMarkup((InlineKeyboardMarkup) getReplyKeyboard(updateData))
                .messageId(updateData.getMessage().getCallbackMessageId())
                .build();

        telegramController.sendMessage(message);
    }

    private void buildDeleteMessage(UpdateData updateData) {
        DeleteMessage message = DeleteMessage.builder()
                .chatId(updateData.getMessage().getChatId())
                .messageId(updateData.getMessage().getCallbackMessageId())
                .build();

        telegramController.sendMessage(message);
    }

    private ReplyKeyboard getReplyKeyboard(UpdateData updateData) {
        return switch (updateData.getMessage().getKeyboardType()) {
            case REPLY -> buildReplyButtons(updateData);
            case INLINE -> buildInlineButtons(updateData);
        };
    }

    private InlineKeyboardMarkup buildInlineButtons(UpdateData updateData) {
        Message message = updateData.getMessage();

        if (message.getButtons() != null) {
            List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
            var listOfLists = message.getButtons();

            for (int i = 0; i < listOfLists.size(); i++) {
                List<InlineKeyboardButton> buttons = new ArrayList<>();
                var listOfButtons = listOfLists.get(i);

                for (int j = 0; j < listOfButtons.size(); j++) {
                    buttons.add(InlineKeyboardButton.builder().text(listOfButtons.get(i).getText()).callbackData(listOfButtons.get(i).getCallbackData()).build());
                }
                buttonRows.add(buttons);
            }
            return InlineKeyboardMarkup.builder()
                    .keyboard(buttonRows)
                    .build();
        }
        return null;
    }

    private InlineKeyboardMarkup buildReplyButtons(UpdateData updateData) {
        Message message = updateData.getMessage();

        if (message.getButtons() != null) {
            List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
            var listOfLists = message.getButtons();

            for (int i = 0; i < listOfLists.size(); i++) {
                List<InlineKeyboardButton> buttons = new ArrayList<>();
                var listOfButtons = listOfLists.get(i);

                for (int j = 0; j < listOfButtons.size(); j++) {
                    buttons.add(InlineKeyboardButton.builder().text(listOfButtons.get(i).getText()).callbackData(listOfButtons.get(i).getCallbackData()).build());
                }
                buttonRows.add(buttons);
            }
            return InlineKeyboardMarkup.builder()
                    .keyboard(buttonRows)
                    .build();
        }
        return null;
    }
}
