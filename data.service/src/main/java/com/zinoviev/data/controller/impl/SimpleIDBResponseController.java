package com.zinoviev.data.controller.impl;

import com.zinoviev.data.controller.DBResponseController;
import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/data")
public class SimpleIDBResponseController implements DBResponseController {

    private String orchestratorLink = "http://localhost:24004/api/orch/db/new/response";

    private RequestHandlerService requestHandlerService;

    @Override
    public void setRequestService(RequestHandlerService requestHandlerService) {
        this.requestHandlerService = requestHandlerService;
    }


    @PostMapping("/userdata")
    public String userData(@RequestBody UpdateData updateData) {
        requestHandlerService.processTheRequest(updateData);
        return "OK";
    }


    @Override
    public void sendUserDataResponse(UpdateData updateData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        new RestTemplate().exchange(
                orchestratorLink,
                HttpMethod.POST,
                new HttpEntity<>(updateData, headers),
                UpdateData.class
        );

 /*       if (responseEntity.getStatusCode() == HttpStatus.OK) {
           // String responseBody = responseEntity.getBody();
            System.out.println("Ответ от сервера:");
            //System.out.println(responseBody);
          //  break;
        } else {
            System.err.println("Ошибка при выполнении запроса. Статус код: " + responseEntity.getStatusCodeValue());
        }*/
    }

}
