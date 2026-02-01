package org.falexgl.telegram;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramBridge {

    public void sendMessage(String message, String token, String chatId) throws Exception {
        String text = URLEncoder.encode(message, StandardCharsets.UTF_8);

        String urlString = "https://api.telegram.org/bot"
                + token
                + "/sendMessage?chat_id="
                + chatId
                + "&text="
                + text;

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.getInputStream().close();
    }
}
