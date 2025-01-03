package LoggingFramework;

public class start {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        logger.info("Application started.");
        logger.debug("This is a debug message.");
        logger.error("An error occurred.");
        logger.warn("This is a warning.");

    }
}
