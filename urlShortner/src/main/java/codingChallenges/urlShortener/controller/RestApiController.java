package codingChallenges.urlShortener.controller;

import codingChallenges.urlShortener.entity.Request;
import codingChallenges.urlShortener.entity.Response;
import codingChallenges.urlShortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/addUrl")
    public ResponseEntity<String> addUrl( @RequestBody Request url) {

        String uniqueKey = urlService.generateShortUrl(url.getId(),url.getUrl());
        String shortUrl = "http://localhost:8080/" + uniqueKey;

        Response response = new Response(uniqueKey, url.getUrl(), shortUrl);
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/getUrl/{urlKey}")
    public ResponseEntity<String> getUrl(@PathVariable String urlKey) {


        return new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
    }
}
