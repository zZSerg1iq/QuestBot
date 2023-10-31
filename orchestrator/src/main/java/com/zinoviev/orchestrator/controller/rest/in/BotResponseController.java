package com.zinoviev.orchestrator.controller.rest.in;

import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BotResponseController {


    @PostMapping("/userdata")
    ResponseEntity<String> dbUserDataResponse(@RequestBody UpdateData updateData);

}
