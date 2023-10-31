package com.zinoviev.data.controller.out;

import com.zinoviev.data.service.entity.UpdateData;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SimpleDBResponseController implements DBResponseController {


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
