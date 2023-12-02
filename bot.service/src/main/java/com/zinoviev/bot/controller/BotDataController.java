package com.zinoviev.bot.controller;

import com.zinoviev.entity.dto.update.UpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface BotDataController {

    void sendUpdateDataToDB(UpdateDto updateDto);

    ResponseEntity<UpdateDto> orchestratorResponse(@RequestBody UpdateDto updateDto);

}
