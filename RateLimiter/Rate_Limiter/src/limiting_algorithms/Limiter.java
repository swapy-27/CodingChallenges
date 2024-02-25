package limiting_algorithms;

public interface Limiter {


    Boolean grantAccess(String ipAddress);
}
