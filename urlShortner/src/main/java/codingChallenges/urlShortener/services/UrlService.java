package codingChallenges.urlShortener.services;

import codingChallenges.urlShortener.configuration.ClientNames;
import codingChallenges.urlShortener.configuration.DBContextHolder;
import codingChallenges.urlShortener.entity.Response;
import codingChallenges.urlShortener.entity.ResponseDTO;
import codingChallenges.urlShortener.entity.URL;
import codingChallenges.urlShortener.repositories.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class UrlService {
    @Autowired
    private UrlShortenerRepository urlShortenerRepository;


    public Response generateShortUrl(String originalUrl) {
        //check if exists return same
        //else generate new Url
        //if any error return Error DTO

        URL url = urlShortenerRepository.findByOriginalUrl(originalUrl);

        if (url != null) {
            String shortUrl = "http://localhost:8080/" + url.getUniqueKey();
            return new ResponseDTO(url.getUniqueKey(), url.getOriginalUrl(), shortUrl);
        }

        String uniqueKey = generateUniqueKey(originalUrl);
        ClientNames db = getDatabaseIndex(uniqueKey, 3);
        DBContextHolder.setCurrentDb(db);

        URL newUrl = new URL(uniqueKey, originalUrl);
        urlShortenerRepository.save(newUrl);
        String shortUrl = "http://localhost:8080/" + newUrl.getUniqueKey();
        return new ResponseDTO(newUrl.getUniqueKey(), newUrl.getOriginalUrl(), shortUrl);

    };


    public String getOriginalUrl(String uniqueKey) {
        URL url = urlShortenerRepository.findByUniqueKey(uniqueKey);

        if (null == url) {
            return "";
        }

        return url.getOriginalUrl();
    };

    public Boolean removeUrl(String uniqueKey) {
        URL url = urlShortenerRepository.findByUniqueKey(uniqueKey);
        urlShortenerRepository.delete(url);
        return true;
    };


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
        if (url != null) {
            return "found in database: " + client + " with values " + url.toString();
        }
        return "found in " + client + " nada!";
    }


    public String createUrl(String client, URL url) {

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

        URL entity = urlShortenerRepository.save(url);
        if (url != null) {
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


//    public Boolean isUrlExists(String url) {
//        String val = urlShortenerRepository.findByOriginalUrl(url);
//        return null != val ? true : false;
//    }


    public String generateUniqueKey(String originalUrl) {
        try {
            // Using SHA-256 hash function
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(originalUrl.getBytes());

            // Convert the byte array to a numeric value
            long numericHash = convertBytesToLong(hashBytes);

            // Convert the numeric hash to Base62
            String base62Key = toBase62(numericHash);

            return base62Key;
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }

    private long convertBytesToLong(byte[] bytes) {
        // Convert byte array to long
        long numericValue = 0;
        for (int i = 0; i < 8; i++) {
            numericValue = (numericValue << 8) | (bytes[i] & 0xFFL);
        }
        return Math.abs(numericValue);
    }

    private String toBase62(long value) {
        // Convert numeric value to Base62
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder base62Key = new StringBuilder();

        while (value > 0) {
            int remainder = (int) (value % 62);
            base62Key.insert(0, characters.charAt(remainder));
            value /= 62;
        }

        return base62Key.toString();
    }

    public ClientNames getDatabaseIndex(String uniqueKey, int numberOfDatabases) {
        // Calculate the hash code of the unique key
        int hashCode = uniqueKey.hashCode();

        // Ensure hashCode is non-negative
        long nonNegativeHashCode = Math.abs((long) hashCode);

        // Map the hash code to one of the databases using modulo
        int databaseIndex = (int) (nonNegativeHashCode % numberOfDatabases);


        switch (databaseIndex) {
            case 1:
                return ClientNames.DB2;
            case 2:
                return ClientNames.DB3;
            default:
                return ClientNames.DB1;
        }
    }

}
