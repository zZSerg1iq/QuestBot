package com.zinoviev.data.controller.in;

import com.zinoviev.data.handler.RequestService;
import com.zinoviev.data.service.entity.UpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
