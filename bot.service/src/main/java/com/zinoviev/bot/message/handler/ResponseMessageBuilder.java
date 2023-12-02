package com.zinoviev.bot.message.handler;

import com.zinoviev.entity.dto.update.UpdateDto;

public interface ResponseMessageBuilder {

    void buildMessage(UpdateDto updateDto);

}
