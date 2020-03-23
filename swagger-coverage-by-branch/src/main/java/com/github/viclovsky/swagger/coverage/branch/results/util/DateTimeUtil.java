package com.github.viclovsky.swagger.coverage.branch.results.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;


public class DateTimeUtil {

    protected static DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            ;

    public static String formatDate(Instant instant){
        return dateTimeFormatter.format(instant);
    }
}
