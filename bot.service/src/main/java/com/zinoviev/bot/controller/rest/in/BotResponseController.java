package com.zinoviev.bot.controller.rest.in;

import com.zinoviev.bot.entity.rest.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BotResponseController {


    @PostMapping("/userdata")
    ResponseEntity<String> dbUserDataResponse(@RequestBody UpdateData updateData);

}