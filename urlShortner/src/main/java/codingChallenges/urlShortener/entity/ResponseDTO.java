package codingChallenges.urlShortener.entity;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class ResponseDTO implements Serializable,Response{

    private String key;
    private String long_url;
    private String short_url;


}
