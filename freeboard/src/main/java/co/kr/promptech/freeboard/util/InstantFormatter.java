package co.kr.promptech.freeboard.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantFormatter {
    private static final String DTO_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DTO_TIME_FORMAT).withZone(ZoneId.systemDefault());
    public static String formatString(Instant instant){
        if(instant == null) return "-";
        return formatter.format(instant);
    }
}