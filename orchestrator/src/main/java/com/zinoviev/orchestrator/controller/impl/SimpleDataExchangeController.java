package com.zinoviev.orchestrator.controller.impl;

import com.zinoviev.entity.model.UpdateData;
import com.zinoviev.orchestrator.controller.DataExchangeController;
import com.zinoviev.orchestrator.enums.ServiceNames;
import com.zinoviev.orchestrator.handler.UpdateDataHandler;
import com.zinoviev.orchestrator.handler.impl.SimpleUpdateDataDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("api/orch")
public class SimpleDataExchangeController implements DataExchangeController {

    //@Value("${bot.service}")
    private String botService = "http://localhost:24001/api/bot/orch/response";

    //@Value("${data.service}")
    private String dataService ="http://localhost:24002/api/data/userdata";

    //@Value("${quest.service}")
    private String questService = "http://localhost:24005/api/bot/userdata";

    private final UpdateDataHandler updateDataHandler;


    @Autowired
    public SimpleDataExchangeController() {
        updateDataHandler = new SimpleUpdateDataDataHandler(this);
    }

    @PostMapping("/bot/new/request")
    public ResponseEntity<String> getBotRequest(UpdateData updateData) {
        updateDataHandler.addRequest(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/db/new/response")
    public ResponseEntity<String> getDBResponse(UpdateData updateData) {
        updateDataHandler.addRequest(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void sendDataTo(ServiceNames service, UpdateData updateData) {
        String serviceLink = null;
        switch (service){
            case BOT_SERVICE -> serviceLink = botService;
            case DATA_SERVICE -> serviceLink = dataService;
            case QUEST_SERVICE -> serviceLink = questService;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<UpdateData> responseEntity = new RestTemplate().exchange(
                    serviceLink,
                    HttpMethod.POST,
                    new HttpEntity<>(updateData, headers),
                    UpdateData.class
            );
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                System.err.println("Ошибка при выполнении запроса. Статус код: " + responseEntity.getStatusCodeValue());
            }
        } catch (RestClientException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }
}
