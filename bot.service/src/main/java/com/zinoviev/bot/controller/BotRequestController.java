package com.zinoviev.bot.controller;

import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BotRequestController {

    void sendUpdateDataToOrchestrator(UpdateData updateData);

    @PostMapping("/orchrsp")
    ResponseEntity<String> orchestratorResponse(@RequestBody UpdateData updateData);

}
