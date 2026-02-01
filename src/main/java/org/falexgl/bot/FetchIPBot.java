package org.falexgl.bot;

import org.falexgl.helpers.date.DateFormatted;
import org.falexgl.helpers.ip.IPHandler;
import org.falexgl.helpers.settings.SettingFileHelper;
import org.falexgl.models.Credentials;
import org.falexgl.telegram.TelegramBridge;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FetchIPBot {

    private static FetchIPBot instance;
    private ScheduledExecutorService scheduler;
    private IPHandler ipHandler = new IPHandler();

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
                System.out.println(DateFormatted.getFormattedDate() + " New IP obtained: " + ipHandler.storedIp);
                sendMessage(ipHandler);
            } else {
                System.out.println(DateFormatted.getFormattedDate() + " No new IP found.");
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.MINUTES);
    }

    public void stopBot() {
        if (scheduler != null) {
            scheduler.shutdown();
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
        } catch (Exception e) {
            System.out.println(DateFormatted.getFormattedDate() + " Error sending Telegram message: " + e.getMessage());
        }
    }
}
