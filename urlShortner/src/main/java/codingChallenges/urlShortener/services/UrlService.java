package codingChallenges.urlShortener.services;

import codingChallenges.urlShortener.configuration.ClientNames;
import codingChallenges.urlShortener.configuration.DBContextHolder;
import codingChallenges.urlShortener.entity.Entity1;
import codingChallenges.urlShortener.entity.URL;
import codingChallenges.urlShortener.repositories.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class UrlService {
    @Autowired
    private UrlShortenerRepository urlShortenerRepository;


    public String getClientNames(String client) {
        switch (client) {
            case "db1":
                DBContextHolder.setCurrentDb(ClientNames.DB1);
                break;
            case "db2":
                DBContextHolder.setCurrentDb(ClientNames.DB2);
                break;
            case "db3":
                DBContextHolder.setCurrentDb(ClientNames.DB3);
                break;
        }
      URL url = urlShortenerRepository.findByUniqueKey("abcdef1234");
        if(url != null) {
            return "found in database: " + client + " with values " + url.toString();
        }
        return "found in " + client + " nada!";
    }

    public String createUrl(String client , URL url){

        switch (client) {
            case "db1":
                DBContextHolder.setCurrentDb(ClientNames.DB1);
                break;
            case "db2":
                DBContextHolder.setCurrentDb(ClientNames.DB2);
                break;
            case "db3":
                DBContextHolder.setCurrentDb(ClientNames.DB3);
                break;
        }

        URL entity  = urlShortenerRepository.save(url);
        if(url != null) {
            return "found in database: " + client + " with values " + url.toString();
        }
        return "found in " + client + " nada!";

    }

//    public String generateShortUrl( String longUrl) {

//        byte[] hashBytes = getKey(longUrl);
//
//
//        // Convert the byte array to a hexadecimal string
//        String uniqueKey = Base64.getEncoder().encodeToString(hashBytes);
//        URL url = new URL(id, uniqueKey, longUrl);
//
//
//        ClientNames db = getDBServer(hashBytes);
//
//        DBContextHolder.setCurrentDb(db);
//
//        urlShortenerRepository.save(url);
//

//        return uniqueKey;
//    }

    ;


    public ClientNames getDBServer(byte[] hashBytes) {
        long numericHash = convertBytesToLong(hashBytes);

        // Calculate the remainder when divided by 3
        int remainder = (int) (numericHash % 3);
        switch (remainder) {
            case 1:
                return ClientNames.DB1;

            case 2:
                return ClientNames.DB2;

            case 0:
                return ClientNames.DB3;

        }
        return ClientNames.DB1;
    }

    public byte[] getKey(String url) {
        try {
            // Using SHA-256 hash function
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(url.getBytes());
            return hashBytes;
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    ;

    private long convertBytesToLong(byte[] bytes) {
        // Convert byte array to long
        long numericValue = 0;
        for (int i = 0; i < 8; i++) {
            numericValue = (numericValue << 8) | (bytes[i] & 0xFF);
        }
        return numericValue;
    }

    public Boolean isUrlExists(String url) {
        String val = urlShortenerRepository.findByOriginalUrl(url);
        return null != val ? true : false;
    }

    public String getOriginalUrl(String uniqueKey) {
        URL url = urlShortenerRepository.findByUniqueKey(uniqueKey);

        if (null == url) {
            return "";
        }

        return url.getOriginalUrl();
    }

    ;

    public Boolean removeUrl(String uniqueKey) {
        URL url = urlShortenerRepository.findByUniqueKey(uniqueKey);
        urlShortenerRepository.delete(url);
        return true;
    }

    ;


}
