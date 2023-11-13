package com.zinoviev.orchestrator.service;

import com.zinoviev.entity.enums.MessageType;
import com.zinoviev.entity.model.updatedata.entity.InlineButton;
import com.zinoviev.entity.model.updatedata.entity.Message;
import com.zinoviev.entity.model.updatedata.entity.ReplyKeyboardType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageBuilderService {

    public void buildInlineMessage(Message message, MessageType messageType, String messageText, String[] buttonNames, String[] buttonCallback) {
        List<List<InlineButton>> buttonRows = new ArrayList<>();
        List<InlineButton> buttons;

        for (int i = 0; i < buttonNames.length; i++) {
            buttons = new ArrayList<>();
            buttons.add(new InlineButton.Builder()
                    .setText(buttonNames[i])
                    .setCallbackData(buttonCallback[i])
                    .build());
            buttonRows.add(buttons);
        }

        message.setMessageType(messageType);
        message.setText(messageText);
        message.setKeyboardType(ReplyKeyboardType.INLINE);
        message.setButtons(buttonRows);
    }

}
