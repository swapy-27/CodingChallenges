package codingChallenges.urlShortener.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDTO implements  Response{


    private String error;
    private Integer statusCode;

}
