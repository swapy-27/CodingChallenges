package limiting_algorithms;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TokenBucket implements Limiter {


    private Map<String, Bucket> userMap;

    TokenBucket() {
        userMap = new HashMap<>();
    }


    @Override
    public Boolean grantAccess() {
        return null;
    }

    @Override
    public Boolean grantAccess(String ipAddress) {

        Bucket tokenBucket = userMap.getOrDefault(ipAddress, null);

        if (null != tokenBucket) {
            return tokenBucket.getSize() > 0 ? true : false;
        } else {
            userMap.put(ipAddress, new Bucket());
        }
        return true;

    }


    class Bucket {

        Queue<String> queue;

        int maxTokens;
        int refillRate;

        Bucket() {

            this.maxTokens = 10;
            this.refillRate = 1;
            this.queue = new LinkedList<>();

        }

        public int getSize() {
            return queue.size();
        }

        public void removeToken(){
            queue.remove();
        }
    }
}
