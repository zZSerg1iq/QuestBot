package com.zinoviev.bot.message.handler;

import com.zinoviev.bot.controller.TelegramController;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.dto.update.include.MessageDto;
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

    public void buildMessage(UpdateDto updateDto) {
        buildMessageBody(updateDto);
    }

    private void buildMessageBody(UpdateDto updateDto) {
        MessageDto messageDto = updateDto.getMessageDto();
        System.out.println(">> " + messageDto.getMessageType());

        switch (messageDto.getMessageType()) {
            case MESSAGE -> buildSendMessage(updateDto);
            case EDIT_MESSAGE -> buildSendEditTextMessage(updateDto);
            case DELETE -> buildDeleteMessage(updateDto);
        }
    }

    private void buildSendMessage(UpdateDto updateDto) {
        SendMessage message = SendMessage.builder()
                .chatId(updateDto.getMessageDto().getUserId())
                .text(updateDto.getMessageDto().getText())
                .replyMarkup(selectReplyKeyboard(updateDto))
                .build();

        telegramController.sendMessage(message);
    }

    private void buildSendEditTextMessage(UpdateDto updateDto) {
        EditMessageText message = EditMessageText.builder()
                .chatId(updateDto.getMessageDto().getChatId())
                .text(updateDto.getMessageDto().getText())
                .replyMarkup(buildInlineButtons(updateDto.getMessageDto()))
                .messageId(updateDto.getMessageDto().getCallbackMessageId())
                .build();

        telegramController.sendMessage(message);
    }

    private void buildDeleteMessage(UpdateDto updateDto) {
        DeleteMessage message = DeleteMessage.builder()
                .chatId(updateDto.getMessageDto().getChatId())
                .messageId(updateDto.getMessageDto().getCallbackMessageId())
                .build();

        telegramController.sendMessage(message);
    }

    private ReplyKeyboard selectReplyKeyboard(UpdateDto updateDto) {
        System.out.println(">>> " + updateDto.getMessageDto().getKeyboardType());

        return switch (updateDto.getMessageDto().getKeyboardType()) {
            case REPLY_ADD -> getReplyKeyboard(updateDto);
            case REPLY_REMOVE -> removeReplyKeyboard();
            case INLINE -> buildInlineButtons(updateDto.getMessageDto());
            case NULL -> null;
        };
    }

    public ReplyKeyboard getReplyKeyboard(UpdateDto updateDto) {
        if (updateDto.getUserDTO().getPlayer() != null) {

            return switch (updateDto.getUserDTO().getPlayer().getGameRole()) {
                case PLAYER -> getPlayerReplyKeyboardMarkup();
                case ADMIN -> getAdminReplyKeyboardMarkup();
            };
        }
        return getUserReplyKeyboardMarkup();
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


    private InlineKeyboardMarkup buildInlineButtons(MessageDto messageDto) {
        if (messageDto.getButtons() != null) {
            List<List<InlineKeyboardButton>> botButtonRows = new ArrayList<>();
            var buttonsList = messageDto.getButtons();

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
