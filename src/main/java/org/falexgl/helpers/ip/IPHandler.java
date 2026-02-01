package org.falexgl.helpers.ip;

import org.falexgl.utils.log.AppLogger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class IPHandler {

    public String storedIp = "";

    public boolean isNewIp() {
        try {
            String ip = getPublicIP();
            if (!ip.equals(storedIp)) {
                storedIp = ip;
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            AppLogger.error(Level.SEVERE, "Checking IP error", e);
            return false;
        }
    }

    private String getPublicIP() throws Exception {
        URL url = new URL("https://api.ipify.org");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            return br.readLine();
        }
    }
}
