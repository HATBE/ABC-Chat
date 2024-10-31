package ch.hatbe2113.abcchat.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

    private LogManager() {
        // private constructor to prevent instantiation
    }

    public static Logger getLogger() {
        // get the class (name) that sends the log request
        String className = new Throwable().getStackTrace()[2].getClassName();
        return LoggerFactory.getLogger(className);
    }
}