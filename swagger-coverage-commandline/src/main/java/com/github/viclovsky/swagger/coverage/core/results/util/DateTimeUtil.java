package com.github.viclovsky.swagger.coverage.core.results.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DateTimeUtil {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static String formatDate(Instant instant) {
        return dateTimeFormatter.format(instant);
    }
}
