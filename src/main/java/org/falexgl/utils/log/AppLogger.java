package org.falexgl.utils.log;

import java.util.logging.*;

public class AppLogger {
    private static final Logger logger = Logger.getLogger("AppLogger");

    static {
        try {
            FileHandler fileHandler = new FileHandler("app_%g.log", 1024 * 1024, 5, true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord logRecord) {
                    return String.format(
                            "%1$tF %1$tT [%2$s] %3$s%n",
                            logRecord.getMillis(),
                            logRecord.getLevel().getName(), // INFO, WARNING, SEVERE
                            logRecord.getMessage()
                    );
                }
            });
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            System.out.println("Error handling log file: " + e.getMessage());
        }
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(java.util.logging.Level level, String message, Exception e) {
        logger.log(level, message, e);
    }
}
