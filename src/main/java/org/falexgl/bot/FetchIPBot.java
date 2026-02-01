package org.falexgl.bot;

import org.falexgl.helpers.ip.IPHandler;
import org.falexgl.utils.log.AppLogger;
import org.falexgl.utils.settings.SettingFileHelper;
import org.falexgl.models.Credentials;
import org.falexgl.telegram.TelegramBridge;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class FetchIPBot {

    private static FetchIPBot instance;
    private ScheduledExecutorService scheduler;
    private final IPHandler ipHandler = new IPHandler();

    public static FetchIPBot getInstance() {
        if (instance == null) {
            instance = new FetchIPBot();
        }
        return instance;
    }

    public void initFetchIpBot(Integer period) {
        scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            if (ipHandler.isNewIp()) {
                AppLogger.info("New IP obtained: " + ipHandler.storedIp);
                sendMessage(ipHandler);
            } else {
                AppLogger.info("No new IP found.");
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.MINUTES);
        AppLogger.info("Running bot every " + period + " minutes.");
        System.out.println("Bot running...");
        System.out.println();
    }

    public void stopBot() {
        if (scheduler != null) {
            scheduler.shutdown();
            AppLogger.info("Bot stopped.");
            System.out.println("Bot stopped.");
        }
    }

    public Boolean isBotUp() {
        return scheduler != null && !scheduler.isShutdown();
    }

    private static void sendMessage(IPHandler ipHandler) {
        Credentials credentials = SettingFileHelper.getCredentials();
        TelegramBridge bot = new TelegramBridge();
        try {
            bot.sendMessage(ipHandler.storedIp, credentials.token, credentials.chatID);
            AppLogger.info("New IP sent to Telegram: " + ipHandler.storedIp);
        } catch (Exception e) {
            AppLogger.error(Level.SEVERE, "Error sending Telegram Message with new IP", e);
        }
    }
}
