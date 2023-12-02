package com.zinoviev.data.controller;

import com.zinoviev.data.handler.RequestHandlerService;
import com.zinoviev.entity.dto.update.UpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ExchangeController {

    void setRequestService(RequestHandlerService requestHandlerService);

    ResponseEntity<UpdateDto> userData(@RequestBody UpdateDto updateDto);

    ResponseEntity<UpdateDto> playerData(@RequestBody UpdateDto updateDto);

    ResponseEntity<UpdateDto> activeQuestData(@RequestBody UpdateDto updateDto);

    ResponseEntity<UpdateDto> questCreationData(@RequestBody UpdateDto updateDto);

    void sendUserDataResponse(UpdateDto updateDto);

}
