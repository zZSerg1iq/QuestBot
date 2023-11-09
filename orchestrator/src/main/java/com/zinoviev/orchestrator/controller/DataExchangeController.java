package com.zinoviev.orchestrator.controller;

import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.enums.ServiceNames;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface DataExchangeController {

    void sendDataTo(ServiceNames service, UpdateData updateData);

    ResponseEntity<String> getBotRequest(@RequestBody UpdateData updateData);

    ResponseEntity<String> getDBResponse(@RequestBody UpdateData updateData);
}
