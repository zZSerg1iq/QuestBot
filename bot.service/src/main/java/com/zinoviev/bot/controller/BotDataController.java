package com.zinoviev.bot.controller;

import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface BotDataController {

    void sendUpdateDataToDB(UpdateData updateData);


    ResponseEntity<String> orchestratorResponse(@RequestBody UpdateData updateData);

}
