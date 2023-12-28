package codingChallenges.urlShortener.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request {

    private Long id ;
    private String url;

}
