package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private ApiService service;

    @GetMapping("/invokeUrl")
    public ResponseEntity<String> invokeUrl(){

        service.invokeUrl();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
