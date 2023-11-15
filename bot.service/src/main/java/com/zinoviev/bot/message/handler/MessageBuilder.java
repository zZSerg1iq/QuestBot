package com.zinoviev.bot.message.handler;

import com.zinoviev.bot.controller.TelegramController;
import com.zinoviev.entity.enums.Role;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.Message;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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
        System.out.println(">> "+message.getMessageType());


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
                .replyMarkup(selectReplyKeyboard(updateData))
                .build();

        telegramController.sendMessage(message);
    }

    private void buildSendEditTextMessage(UpdateData updateData) {
        EditMessageText message = EditMessageText.builder()
                .chatId(updateData.getMessage().getChatId())
                .text(updateData.getMessage().getText())
                .replyMarkup(buildInlineButtons(updateData.getMessage()))
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

    private ReplyKeyboard selectReplyKeyboard(UpdateData updateData) {
        System.out.println(">>> "+ updateData.getMessage().getKeyboardType());

        return switch (updateData.getMessage().getKeyboardType()) {
            case REPLY_ADD -> getReplyKeyboard(updateData);
            case REPLY_REMOVE -> removeReplyKeyboard();
            case INLINE -> buildInlineButtons(updateData.getMessage());
            case NULL -> null;
        };
    }

    public ReplyKeyboard getReplyKeyboard(UpdateData updateData) {
        return switch (updateData.getUserData().getRole()){
            case USER -> getUserReplyKeyboardMarkup();
            case PLAYER -> getPlayerReplyKeyboardMarkup();
            case ADMIN -> getAdminReplyKeyboardMarkup();
        };
    }

    private ReplyKeyboardMarkup getUserReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Аккаунт");
        keyboardRow.add("Квесты");
        keyboardRow.add("Справка");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше
        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup getPlayerReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("PLAY PLAY");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше
        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup getAdminReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboards = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("ADMIN ADMIN");
        keyboards.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboards);
        keyboardMarkup.setResizeKeyboard(true); // делает кнопочки меньше
        return keyboardMarkup;
    }

    public ReplyKeyboardRemove removeReplyKeyboard() {
        System.out.println("REMOVE");
        return ReplyKeyboardRemove.builder().removeKeyboard(true).build();
    }


    private InlineKeyboardMarkup buildInlineButtons(Message message) {
        if (message.getButtons() != null) {
            List<List<InlineKeyboardButton>> botButtonRows = new ArrayList<>();
            var buttonsList = message.getButtons();

            for (int listRow = 0; listRow < buttonsList.size(); listRow++) {
                List<InlineKeyboardButton> botButtons = new ArrayList<>();
                var buttons = buttonsList.get(listRow);

                for (int buttonRow = 0; buttonRow < buttons.size(); buttonRow++) {
                    String name = buttons.get(buttonRow).getText();
                    String callback = buttons.get(buttonRow).getCallbackData();

                    botButtons.add(InlineKeyboardButton
                            .builder()
                            .text(name)
                            .callbackData(callback)
                            .build());
                }
                botButtonRows.add(botButtons);
            }
            return InlineKeyboardMarkup.builder()
                    .keyboard(botButtonRows)
                    .build();
        }
        return null;
    }

}
