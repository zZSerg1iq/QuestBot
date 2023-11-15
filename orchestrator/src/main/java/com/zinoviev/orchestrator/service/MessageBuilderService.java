package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.entity.model.updatedata.entity.InlineButton;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageBuilderService {

    public MessageBuilderService setMessageType(UpdateData updateData, MessageType messageType) {
        updateData.getMessage().setMessageType(messageType);
        return this;
    }

    public MessageBuilderService setKeyboardType(UpdateData updateData, KeyboardType keyboardType) {
        updateData.getMessage().setKeyboardType(keyboardType);
        return this;
    }

    public MessageBuilderService setText(UpdateData updateData, String text) {
        updateData.getMessage().setText(text);
        return this;
    }

    public MessageBuilderService setButtonsAndCallbacks(UpdateData updateData, String[] buttonNames, String[] buttonCallback) {
        List<List<InlineButton>> buttonRows = new ArrayList<>();
        List<InlineButton> buttons = new ArrayList<>();

        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equalsIgnoreCase(">")) {
                buttonRows.add(buttons);
                buttons = new ArrayList<>();
                continue;
            }

            buttons.add(new InlineButton.Builder()
                    .setText(buttonNames[i])
                    .setCallbackData(buttonCallback[i])
                    .build());
        }
        buttonRows.add(buttons);
        updateData.getMessage().setButtons(buttonRows);
        return this;
    }



   /* public void buildReplyMessage(Message message, MessageType messageType, KeyboardType keyboardType, String messageText) {
        message.setMessageType(messageType);
        message.setText(messageText);
        message.setKeyboardType(keyboardType);
    }*/


}
