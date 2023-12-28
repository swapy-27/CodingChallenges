package codingChallenges.urlShortener.services;

import codingChallenges.urlShortener.entity.URL;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


@Service
public class UrlService {

    public String generateShortUrl(String longUrl){
        String uniqueKey = getKey(longUrl);
        URL url = new URL(uniqueKey,longUrl);




        return "";
    };

    public String getKey(String url){
        String sha256hex = DigestUtils.sha256Hex(url);
        return sha256hex;
    };


}
