package codingChallenges.urlShortener.controller;

import codingChallenges.urlShortener.entity.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    @PostMapping("/addUrl")
    public ResponseEntity<String> addUrl(@RequestBody String url){




        Response response = new Response("",url,"");
        return new ResponseEntity<>(response.toString(),HttpStatus.OK);
    }

    @GetMapping("/getUrl/{urlKey}")
    public ResponseEntity<String> getUrl(@PathVariable String urlKey){


        return new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
    }
}
