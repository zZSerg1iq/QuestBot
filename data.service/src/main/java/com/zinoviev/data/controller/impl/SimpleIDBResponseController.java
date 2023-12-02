package com.zinoviev.data.controller.impl;

import com.zinoviev.data.controller.ExchangeController;
import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.entity.dto.update.UpdateDto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/data")
public class SimpleIDBResponseController implements ExchangeController {

    private String orchestratorLink = "http://localhost:24004/api/orch/db/userData";

    private RequestHandlerService requestHandlerService;

    @Override
    public void setRequestService(RequestHandlerService requestHandlerService) {
        this.requestHandlerService = requestHandlerService;
    }

    @Override
    @PostMapping("/userData")
    public ResponseEntity<UpdateDto> userData(@RequestBody UpdateDto updateDto) {
        requestHandlerService.processTheRequest(updateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping("/playerData")
    public ResponseEntity<UpdateDto> playerData(UpdateDto updateDto) {
        return null;
    }

    @Override
    @PostMapping("/activeQuestData")
    public ResponseEntity<UpdateDto> activeQuestData(UpdateDto updateDto) {
        return null;
    }

    @Override
    @PostMapping("/questCreationData")
    public ResponseEntity<UpdateDto> questCreationData(UpdateDto updateDto) {
        return null;
    }

    @Override
    public void sendUserDataResponse(UpdateDto updateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<UpdateDto> responseEntity = new RestTemplate().exchange(
                    orchestratorLink,
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
