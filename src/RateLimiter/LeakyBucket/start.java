package RateLimiter.LeakyBucket;


public class start {
    public static void main(String[] args) throws InterruptedException {
        int maxRequest = 5;
        long leakRateMs = 1;
        LeakyBucket leakyBucket = new LeakyBucket(maxRequest, leakRateMs); //max 5 request, leak 1 token/second
        String user = "Sanskar";

        for (int i = 0; i < 20; i++) {
            if (leakyBucket.allowRequest(user)) {
                System.out.println("Request of " + user + " " + (i + 1) + " allowed.");
            } else {
                System.out.println("Request of " + user + " " + (i + 1) + " denied.");
            }
            Thread.sleep(200);
        }
    }
}
