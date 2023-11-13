package com.zinoviev.data.controller;

import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface DBResponseController {

    void setRequestService(RequestHandlerService requestHandlerService);

    ResponseEntity<UpdateData> userData(@RequestBody UpdateData updateData);

    void sendUserDataResponse(UpdateData updateData);

}
