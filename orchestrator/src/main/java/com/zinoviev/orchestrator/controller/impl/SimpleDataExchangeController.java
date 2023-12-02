package com.zinoviev.orchestrator.controller.impl;

import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.enums.RequestType;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceURL;
import com.zinoviev.orchestrator.handler.UpdateDataHandler;
import com.zinoviev.orchestrator.handler.impl.SimpleUpdateDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@RequestMapping("api/orch")
public class SimpleDataExchangeController implements DataExchangeController {

    private final UpdateDataHandler updateDataHandler;

    @Autowired
    public SimpleDataExchangeController() {
        updateDataHandler = new SimpleUpdateDataHandler(this);
    }

    @PostMapping("/bot/new/request")
    public ResponseEntity<UpdateDto> getBotRequest(UpdateDto updateDto) {
       // updateDataHandler.addRequest(updateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/db/userData")
    public ResponseEntity<UpdateDto> userData(@RequestBody UpdateDto updateDto) {
        System.out.println(updateDto);
        updateDataHandler.addRequest(updateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void exchangeWith(ServiceURL serviceLink, UpdateDto updateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<UpdateDto> responseEntity = new RestTemplate().exchange(
                    serviceLink.getURL(),
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
