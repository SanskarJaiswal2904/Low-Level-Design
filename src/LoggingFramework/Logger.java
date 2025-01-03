package LoggingFramework;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    public void info(String msg){
        formatMessage("INFO", msg);
    }
    public void debug(String msg){
        formatMessage("DEBUG", msg);
    }
    public void error(String msg){
        formatMessage("ERROR", msg);
    }

    public void warn(String msg){
        formatMessage("WARN", msg);
    }

    private void formatMessage(String logLevel, String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + now.format(formatter) + "] [" + logLevel + "] " + message);
    }
}
