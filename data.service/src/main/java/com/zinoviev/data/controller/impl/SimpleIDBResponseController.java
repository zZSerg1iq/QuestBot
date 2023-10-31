package com.zinoviev.data.controller.impl;

import com.zinoviev.data.controller.DBResponseController;
import com.zinoviev.data.handler.RequestService;
import com.zinoviev.entity.model.UpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/data")
public class
SimpleIDBResponseController implements DBResponseController {

    private final RequestService requestService;

    @Autowired
    public SimpleIDBResponseController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/userdata")
    public ResponseEntity<String> userData(@RequestBody UpdateData updateData) {
        requestService.processTheRequest(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public void sendUserDataResponse(UpdateData updateData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<UpdateData> responseEntity = new RestTemplate().exchange(
                "http://localhost:24001/api/bot/userdata",
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
