package com.practice.librarysystem.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeFormatConstants {
    private static final String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static DateTimeFormatter getDefaultFormatter() {
        return DateTimeFormatter.ofPattern(FORMAT_PATTERN);
    }

    public static LocalDateTime parseToLocalDateTime(String str) {
        return LocalDateTime.parse(str, getDefaultFormatter());
    }

    public static String formatToString(LocalDateTime dateTime) {
        return dateTime.format(getDefaultFormatter());
    }
}

