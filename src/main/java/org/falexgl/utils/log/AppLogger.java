package org.falexgl.utils.log;

import org.falexgl.cli.menu.MainMenu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.*;
import java.util.stream.Stream;

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

    public static void showLogs() {
        Path root = Paths.get("").toAbsolutePath();
        String regex = "app_(\\d+)\\.log";

        try (var stream = Files.list(root)) {
            Optional<Path> latestFile = stream
                    .filter(p -> p.getFileName().toString().matches(regex))
                    .max(Comparator.comparingInt(p -> {
                        String name = p.getFileName().toString();
                        return Integer.parseInt(name.replaceAll("app_|\\.log", ""));
                    }));

            latestFile.ifPresentOrElse(
                    p -> {
                        try (Stream<String> lines = Files.lines(latestFile.get())) {
                            lines.forEach(System.out::println);
                        } catch (IOException e) {
                            AppLogger.error(Level.SEVERE, "Error reading log file: ", e);
                        }
                    },
                    () -> AppLogger.error(Level.WARNING, "Logger file not found.", null)
            );
            goBack();
        } catch (IOException e) {
            AppLogger.error(Level.SEVERE, "Error reading files in directory: ", e);
            goBack();
        }
    }

    private static void goBack() {
        Scanner sc =  new Scanner(System.in);
        System.out.println("Press enter to continue...");
        sc.nextLine();
        MainMenu.getInstance().initMenu();
    }
}
