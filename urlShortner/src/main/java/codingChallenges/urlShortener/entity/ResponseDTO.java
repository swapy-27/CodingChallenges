package codingChallenges.urlShortener.entity;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class ResponseDTO implements Response{

    private String key;
    private String long_url;
    private String short_url;

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "key='" + key + '\'' +
                ", long_url='" + long_url + '\'' +
                ", short_url='" + short_url + '\'' +
                '}';
    }
}
