package limiting_algorithms;

import java.time.Instant;
import java.util.HashMap;

import java.util.Map;


//6 req per second window
public class SlidingWindow implements Limiter{


    Map<Long,Long> window;

    private int maxTime;

    private int capacity;

    private long mapSize;

    public SlidingWindow(){
        window= new HashMap<>();
        window.put(Instant.now().toEpochMilli(), 0L);
        capacity=6;
        maxTime = 10;
        mapSize=1;
    }



    @Override
    public Boolean grantAccess(String ipAddress) {

        Long currTime = Instant.now().toEpochMilli();
        Long startWindow = currTime-maxTime;

        window.put(currTime,mapSize);
        mapSize++;

        if(window.containsKey(startWindow)){
            long reqCount = mapSize-window.get(startWindow);

            return reqCount<capacity ? true : false;
        }

        return true;


    }
}
