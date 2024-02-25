package limiting_algorithms;

public interface Limiter {

    Boolean grantAccess();

    Boolean grantAccess(String ipAddress);
}
