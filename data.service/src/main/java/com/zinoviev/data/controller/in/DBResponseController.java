package com.zinoviev.data.controller.in;

import com.zinoviev.data.service.entity.UpdateData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface DBResponseController {


    ResponseEntity<String> userData(@RequestBody UpdateData updateData);

}
