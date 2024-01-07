package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {


    public static String shortenUrl(String originalUrl) {
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

    private static long convertBytesToLong(byte[] bytes) {
        // Convert byte array to long
        long numericValue = 0;
        for (int i = 0; i < 8; i++) {
            numericValue = (numericValue << 8) | (bytes[i] & 0xFFL);
        }
        return Math.abs(numericValue);
    }

    private static String toBase62(long value) {
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

    public static int getDatabaseIndex(String uniqueKey, int numberOfDatabases) {
        // Calculate the hash code of the unique key
        int hashCode = uniqueKey.hashCode();

        // Ensure hashCode is non-negative
        long nonNegativeHashCode = Math.abs((long) hashCode);

        // Map the hash code to one of the databases using modulo
        int databaseIndex = (int) (nonNegativeHashCode % numberOfDatabases);

        return databaseIndex;
    }

    public static void main(String[] args) {
        String originalUrl = "https://www.s.com";
        String shortUrl = shortenUrl(originalUrl);
        int dbServer = getDatabaseIndex(shortUrl,3);
        System.out.println("Original URL: " + originalUrl);
        System.out.println("Shortened URL: " + shortUrl);
        System.out.println("DB server : " + dbServer);
    }
}
