package org.example;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ApiService {


    private RestClient restClient;

    ApiService(){
        this.restClient=  RestClient.create();
    }


    public void invokeUrl() {
        String name = "USER";
        String password = "USER";
        String authString = name + ":" + password;
        String authStringEnc = new Base64().encodeAsString(authString.getBytes());
        System.out.println("Base64 encoded auth string: " + authStringEnc);


        ResponseEntity<String> result = restClient.get()
                .uri("https://localhost:8081/get")
                .header("Authorization", "Basic " + authStringEnc)
                .retrieve()
                .toEntity(String.class);

        System.out.println("Response status: " + result.getStatusCode());
        System.out.println("Response headers: " + result.getHeaders());
        System.out.println("Contents: " + result.getBody());

    }
}
