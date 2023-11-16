package com.zinoviev.bot.message.handler;

import com.zinoviev.entity.model.UpdateData;

public interface ResponseMessageBuilder {

    void buildMessage(UpdateData updateData);

}
