package org.falexgl.helpers.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatted {
    public static String getFormattedDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm:ss)");
        return date.format(format);
    }
}
