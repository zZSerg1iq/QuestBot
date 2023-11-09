package com.zinoviev.bot.handler;

import com.zinoviev.bot.entity.rest.UpdateData;


public interface ResponseHandler {

    void addDbResponse(UpdateData updateData);

}
