package org.example;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestApiController {


    @GetMapping("/get")
    public ResponseEntity<String> getSomething() {

        return new ResponseEntity<>("Hello TLS working great!", HttpStatus.OK);
    }


}
