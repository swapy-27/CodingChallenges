package limiting_algorithms;

import java.util.LinkedList;
import java.util.Queue;

//6 req per second window
public class SlidingWindow implements Limiter{

    private Queue<Long> window;

    private int maxTime;

    private int capacity;

    public SlidingWindow(){
        window= new LinkedList();
        capacity=6;
        maxTime = 10;
    }



    @Override
    public Boolean grantAccess(String ipAddress) {
        return null;
    }
}
