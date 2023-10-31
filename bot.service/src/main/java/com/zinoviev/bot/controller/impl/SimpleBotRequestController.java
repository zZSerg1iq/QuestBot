package com.zinoviev.bot.controller.impl;

import com.zinoviev.bot.controller.BotRequestController;
import com.zinoviev.bot.sendmessage.service.message.queue.handler.MessageQueueHandler;
import com.zinoviev.entity.model.UpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("api/bot")
public class SimpleBotRequestController implements BotRequestController {

    @Value("&{orchestrator.link}")
    private String orchestratorLink;


    private final MessageQueueHandler messageQueueHandler;

    @Autowired
    public SimpleBotRequestController(MessageQueueHandler messageQueueHandler) {
        this.messageQueueHandler = messageQueueHandler;
    }

    @Override
    public ResponseEntity<String> orchestratorResponse(@RequestBody UpdateData updateData) {
        messageQueueHandler.addDbResponse(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public void sendUpdateDataToOrchestrator(UpdateData updateData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Update> responseEntity = new RestTemplate().exchange(
                orchestratorLink,
                HttpMethod.POST,
                new HttpEntity<>(updateData, headers),
                Update.class
        );

  /*      if (responseEntity.getStatusCode() == HttpStatus.OK) {
           // String responseBody = responseEntity.getBody();
            System.out.println("Ответ от сервера:");
            //System.out.println(responseBody);
          //  break;
        } else {
            System.err.println("Ошибка при выполнении запроса. Статус код: " + responseEntity.getStatusCodeValue());
        }*/
    }


}
