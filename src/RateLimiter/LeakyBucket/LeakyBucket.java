package RateLimiter.LeakyBucket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
public class LeakyBucket {
    private final int maxBucketSize;
    private final long leakRatePerMS;
    private final ConcurrentHashMap<String, Bucket> userBuckets;

    public LeakyBucket(int maxBucketSize, long leakRatePerMS) {
        this.maxBucketSize = maxBucketSize;
        this.leakRatePerMS = leakRatePerMS;
        this.userBuckets = new ConcurrentHashMap<>();
    }
    public boolean allowRequest(String userId){
        Bucket bucket = userBuckets.computeIfAbsent(userId, id -> new Bucket(maxBucketSize,leakRatePerMS));
        return bucket.evaluate();
    }
    private static class Bucket{
        private final int maxCapacity;
        private final long leakRate;
        private final AtomicLong currentTokens;
        private final AtomicLong lastLeakTimeStamp;

        public Bucket(int maxCapacity, long leakRate) {
            this.maxCapacity = maxCapacity;
            this.leakRate = leakRate;
            this.currentTokens = new AtomicLong(0);
            this.lastLeakTimeStamp = new AtomicLong(System.currentTimeMillis());
        }

        public synchronized boolean evaluate(){
            leakToken();

            if(currentTokens.get() < maxCapacity){
                currentTokens.incrementAndGet();
                return true;
            } else {
                return false;
            }
        }

        private void leakToken(){
            long currentTimeMS = System.currentTimeMillis();
            long elapsedTimeMS = currentTimeMS - lastLeakTimeStamp.get();

            long tokensToLeak = (elapsedTimeMS * leakRate)/1000;
            currentTokens.set(Math.max(0, (currentTokens.get() - tokensToLeak)));
            if (tokensToLeak > 0) {
                lastLeakTimeStamp.set(currentTimeMS);
            }
        }

    }
}
