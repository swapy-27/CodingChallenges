package codingChallenges.urlShortener.controller;

import codingChallenges.urlShortener.entity.Request;
import codingChallenges.urlShortener.entity.Response;
import codingChallenges.urlShortener.entity.URL;
import codingChallenges.urlShortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class RestApiController {
    @Autowired
    private UrlService urlService;


    @GetMapping("/get/{clientdb}")
    public String findFromDatabase(@PathVariable String clientdb) {
        return urlService.getClientNames(clientdb);
    }
//
//        @PostMapping("/api/v1/addUrl")
//        public ResponseEntity<String> addUrl(@RequestBody String url) {
//            //check if already exist
////            if(urlService.isUrlExists(url)) {
////                return  new ResponseEntity<>("Url Already exists",HttpStatus.BAD_REQUEST);
////            }
////
////
////            String uniqueKey = urlService.generateShortUrl(url);
////            String shortUrl = "http://localhost:8080/" + uniqueKey;
////
////            Response response = new Response(uniqueKey, shortUrl);
//            urlService.createUrl();
//            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
//        }

    @PostMapping("/api/v1/{clientDB}")
    public ResponseEntity<String> addUrl(@RequestBody URL url , @PathVariable String clientDB) {

        String response = urlService.createUrl(clientDB,url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<String> getUrl(@PathVariable String urlKey) {

        String originalUrl = urlService.getOriginalUrl(urlKey);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, originalUrl);

        return new ResponseEntity<>(responseHeaders, HttpStatus.PERMANENT_REDIRECT);

    }

    @DeleteMapping("/{urlKey}")
    public ResponseEntity<String> removeUrl(@PathVariable String urlKey) {
        if (urlService.removeUrl(urlKey)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
