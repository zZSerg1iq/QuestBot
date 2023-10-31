package com.zinoviev.bot.controller.rest.in;

import com.zinoviev.bot.entity.rest.UpdateData;
import com.zinoviev.bot.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("api/bot")
public class SimpleBotResponseController implements BotResponseController{

    private final ResponseHandler responseHandler;

    @Autowired
    public SimpleBotResponseController(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public ResponseEntity<String> dbUserDataResponse(@RequestBody UpdateData updateData) {
        responseHandler.addDbResponse(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/quest")
    public ResponseEntity<String> questResponse(@RequestBody Update update) {
       // System.out.println(update);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
