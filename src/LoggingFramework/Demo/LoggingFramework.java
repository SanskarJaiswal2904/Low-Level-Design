package LoggingFramework.Demo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Enum for Log Levels
enum LogLevel {
    INFO, DEBUG, WARN, ERROR
}

// Logger Class
class Logger {
    private static Logger instance;
    private static final Lock lock = new ReentrantLock();

    private LogLevel level;
    private PrintWriter consoleWriter;
    private PrintWriter fileWriter;

    private Logger() {
        level = LogLevel.INFO; // Default Log Level
        consoleWriter = new PrintWriter(System.out, true);
    }

    public static Logger getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new Logger();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void setLogLevel(LogLevel level) {
        this.level = level;
    }

    public void setFileOutput(String filePath) throws IOException {
        if (fileWriter != null) {
            fileWriter.close();
        }
        fileWriter = new PrintWriter(new FileWriter(filePath, true), true);
    }

    public void log(LogLevel logLevel, String message) {
        if (logLevel.ordinal() >= level.ordinal()) {
            String formattedMessage = formatMessage(logLevel, message);
            writeToConsole(formattedMessage);
            writeToFile(formattedMessage);
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private String formatMessage(LogLevel logLevel, String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + now.format(formatter) + "] [" + logLevel + "] " + message;
    }

    private void writeToConsole(String message) {
        if (consoleWriter != null) {
            consoleWriter.println(message);
        }
    }

    private void writeToFile(String message) {
        if (fileWriter != null) {
            fileWriter.println(message);
        }
    }
}

public class LoggingFramework {
    public static void main(String[] args) throws IOException {
        // Initialize Logger
        Logger logger = Logger.getInstance();

        // Set log level
        logger.setLogLevel(LogLevel.DEBUG);

        // Optionally, set file output
        logger.setFileOutput("application.log");

        // Log messages
        logger.info("Application started.");
        logger.debug("This is a debug message.");
        logger.error("An error occurred.");
        logger.warn("This is a warning.");
    }
}
