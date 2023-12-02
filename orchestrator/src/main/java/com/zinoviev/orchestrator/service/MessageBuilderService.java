package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.KeyboardType;
import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.dto.update.include.InlineButtonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageBuilderService {

    public MessageBuilderService setMessageType(UpdateDto updateDto, MessageType messageType) {
        updateDto.getMessageDto().setMessageType(messageType);
        return this;
    }

    public MessageBuilderService setKeyboardType(UpdateDto updateDto, KeyboardType keyboardType) {
        updateDto.getMessageDto().setKeyboardType(keyboardType);
        return this;
    }

    public MessageBuilderService setText(UpdateDto updateDto, String text) {
        updateDto.getMessageDto().setText(text);
        return this;
    }

    public MessageBuilderService setButtonsAndCallbacks(UpdateDto updateDto, String[] buttonNames, String[] buttonCallback) {
        List<List<InlineButtonDto>> buttonRows = new ArrayList<>();
        List<InlineButtonDto> buttons = new ArrayList<>();

        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i] == null) {
                buttonRows.add(buttons);
                buttons = new ArrayList<>();
                continue;
            }

            buttons.add(new InlineButtonDto.Builder()
                    .setText(buttonNames[i])
                    .setCallbackData(buttonCallback[i])
                    .build());
        }
        buttonRows.add(buttons);
        updateDto.getMessageDto().setButtons(buttonRows);
        return this;
    }



   /* public void buildReplyMessage(Message message, MessageType messageType, KeyboardType keyboardType, String messageText) {
        message.setMessageType(messageType);
        message.setText(messageText);
        message.setKeyboardType(keyboardType);
    }*/


}
