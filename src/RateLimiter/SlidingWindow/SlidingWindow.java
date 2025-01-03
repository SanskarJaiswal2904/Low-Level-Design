package RateLimiter.SlidingWindow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindow {
    private final int maxRequests;
    private final long windowSizeInMilli;
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> userRequestLogInfos; //using concurrent hashmap to serve multiple request from multiple users

    public SlidingWindow(int maxRequests, long windowSizeInMilli) {
        this.maxRequests = maxRequests;
        this.windowSizeInMilli = windowSizeInMilli;
        this.userRequestLogInfos = new ConcurrentHashMap<> ();
    }

    public boolean allowRequest(String userId){
        long currentTimeMilli = System.currentTimeMillis();
        userRequestLogInfos.putIfAbsent(userId, new ConcurrentLinkedQueue<>()); // if LinkedQueue is not present
        ConcurrentLinkedQueue<Long> requestQueue = userRequestLogInfos.get(userId); // get LinkedQueue
        synchronized (requestQueue){
            /**only having request that are in window size, ie, that are greater than currentTimeMill - window size,
             * if not then remove from requestQueue*/
            while(!requestQueue.isEmpty() && requestQueue.peek() < currentTimeMilli - windowSizeInMilli){
                requestQueue.poll();
            }
            if(requestQueue.size() < maxRequests){ // queue has space to hold more request
                requestQueue.offer(currentTimeMilli);
                return true;
            } else{
                return false;
            }
        }


    }
}
