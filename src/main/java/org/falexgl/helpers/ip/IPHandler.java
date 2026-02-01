package org.falexgl.helpers.ip;

import org.falexgl.helpers.date.DateFormatted;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
            System.out.println(DateFormatted.getFormattedDate() + " Error checking Ip: " + e.getMessage());
            return false;
        }
    }

    private String getPublicIP() throws Exception {
        URL url = new URL("https://api.ipify.org");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            return br.readLine();
        }
    }
}
