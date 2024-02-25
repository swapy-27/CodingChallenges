package limiting_algorithms;

import java.time.Instant;

public class FixedWindow implements Limiter {
    private int count;

    private long windowStart;
    private int windowSize;
    private int requestsAllowed;

    public FixedWindow() {
        windowSize = 60;
        requestsAllowed = 60;
        windowStart = Instant.now().getEpochSecond();
        count = 0;
    }


    @Override
    public synchronized Boolean grantAccess(String ipAddress) {
        resetWindow();
        if (count > requestsAllowed) {
            return false;
        }
        count++;
        return true;
    }

    ;


    public void resetWindow() {
        long currentTime = Instant.now().getEpochSecond();
        if (currentTime >= windowStart + windowSize) {
            // Reset the window if a new window has started
            windowStart = floorToWindow(currentTime);
            count = 0;
        }
        //current time

        //start time + 60 sec - curr time < 0
        // new window need to start and count reset == 0

        // else do nothing


    }

    private long floorToWindow(long timestamp) {
        return timestamp - (timestamp % windowSize);
    }

}
