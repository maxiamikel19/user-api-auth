package com.maxiamikel.userAuthApi.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class InstantConverter {

    public static String convertToString(Long longValue) {
        ZonedDateTime expirationTime = Instant.now()
                .plusSeconds(longValue)
                .atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = expirationTime.format(formatter);
        return formatted;
    }

}
