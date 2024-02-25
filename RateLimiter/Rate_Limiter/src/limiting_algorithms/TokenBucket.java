package limiting_algorithms;

import java.time.Instant;

import java.util.HashMap;

import java.util.Map;


public class TokenBucket implements Limiter {

    private Map<String, Bucket> userMap;

    public TokenBucket() {
        userMap = new HashMap<>();
    }

    @Override
    public Boolean grantAccess() {
        return null;
    }

    @Override
    public synchronized Boolean  grantAccess(String ipAddress) {

        Bucket tokenBucket = userMap.getOrDefault(ipAddress, null);
            if (null != tokenBucket) {
                tokenBucket.refill();
                if (tokenBucket.getSize() == 0) {
                    return false;
                }
                tokenBucket.removeToken();
                userMap.put(ipAddress, tokenBucket);
            } else {
                userMap.put(ipAddress, new Bucket());
            }
        return true;
    }


    class Bucket {

        int token;
        int maxTokens;
        int refillRate;

        long lastRefillTime;

        Bucket() {

            this.maxTokens = 10;
            this.refillRate = 1;
            this.token = maxTokens;
            this.lastRefillTime = Instant.now().getEpochSecond();

        }

        public int getSize() {
            return token;
        }

        public void removeToken() {
            token--;
        }

        public void refill() {
            long currentTime = Instant.now().getEpochSecond();
            long elapsedTime = currentTime - lastRefillTime;
            long tokensToAdd = (elapsedTime * refillRate);
            token = Math.min(maxTokens, token + (int) tokensToAdd);
            lastRefillTime = currentTime;
        }
    }
}
