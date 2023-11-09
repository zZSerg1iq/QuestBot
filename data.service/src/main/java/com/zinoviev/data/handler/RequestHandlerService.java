package com.zinoviev.data.handler;

import com.zinoviev.entity.model.UpdateData;


public interface RequestHandlerService {

    void processTheRequest(UpdateData updateData);
}
