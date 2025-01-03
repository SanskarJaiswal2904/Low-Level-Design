package RateLimiter.SlidingWindow;

public class start {
    public static void main(String[] args) throws InterruptedException {
        int maxRequest = 5;
        long windowSizeInMilli = 10000;
        SlidingWindow slidingWindow = new SlidingWindow(maxRequest, windowSizeInMilli);
        String user = "Sanskar";

        for (int i = 0; i < 30; i++) {
            if(slidingWindow.allowRequest(user)){
                System.out.println("Request of "+ user + " " + (i+1) + " allowed.");
            } else {
                System.out.println("Request of "+ user + " " + (i+1) + " denied.");
            }
            Thread.sleep(1000); // throws Interrupted exception. If we don't use sleep, then all request will be executed very quickly and it will be outside sliding window
        }
    }
}
