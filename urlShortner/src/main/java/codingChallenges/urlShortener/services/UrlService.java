package codingChallenges.urlShortener.services;

import codingChallenges.urlShortener.configuration.ClientNames;
import codingChallenges.urlShortener.configuration.DBContextHolder;
import codingChallenges.urlShortener.entity.URL;
import codingChallenges.urlShortener.repositories.UrlShortenerRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class UrlService {
    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    public String generateShortUrl(Long id, String longUrl) {

        byte[] hashBytes = getKey(longUrl);


        // Convert the byte array to a hexadecimal string
        String uniqueKey = Base64.getEncoder().encodeToString(hashBytes);
        URL url = new URL(id,uniqueKey, longUrl);


        ClientNames db = getDBServer(hashBytes);

        DBContextHolder.setCurrentDb(db);

        urlShortenerRepository.save(url);


        return uniqueKey;
    }

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


}
