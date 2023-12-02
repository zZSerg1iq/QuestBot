package com.zinoviev.orchestrator.controller;

import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.orchestrator.enums.ServiceURL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


public interface DataExchangeController {

    void exchangeWith(ServiceURL serviceLink, UpdateDto updateDto);

    ResponseEntity<UpdateDto> getBotRequest(@RequestBody UpdateDto updateDto);

    ResponseEntity<UpdateDto> userData(@RequestBody UpdateDto updateDto);
}
