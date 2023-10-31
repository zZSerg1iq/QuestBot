package com.zinoviev.bot.controller.rest.out;

import com.zinoviev.entity.model.UpdateData;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SimpleBotRequestController implements BotRequestController {


    @Override
    public void dbUserDataRequest(UpdateData updateData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Update> responseEntity = new RestTemplate().exchange(
                "http://localhost:24002/api/data/userdata",
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
