package com.zinoviev.bot.controller.impl;

import com.zinoviev.bot.controller.BotDataController;
import com.zinoviev.bot.message.handler.ResponseMessageBuilder;
import com.zinoviev.entity.dto.update.UpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("api/bot")
public class SimpleBotDataController implements BotDataController {

    private int this_port = 24001;

    //@Value("${db.service}")
    private final String dbLink = "http://localhost:24002/api/data/userData";

    private final ResponseMessageBuilder responseMessageBuilder;

    @Autowired
    public SimpleBotDataController(ResponseMessageBuilder responseMessageBuilder) {
        this.responseMessageBuilder = responseMessageBuilder;

    }

    @PostMapping("/orch/response")
    public ResponseEntity<UpdateDto> orchestratorResponse(@RequestBody UpdateDto updateDto) {
        System.out.println("---------------------------------------------------------------");
        responseMessageBuilder.buildMessage(updateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void sendUpdateDataToDB(UpdateDto updateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<UpdateDto> responseEntity = new RestTemplate().exchange(
                    dbLink,
                    HttpMethod.POST,
                    new HttpEntity<>(updateDto, headers),
                    UpdateDto.class
            );
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                System.err.println("Ошибка при выполнении запроса. Статус код: " + responseEntity.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }

}
