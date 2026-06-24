package com.bff.application.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private DateUtils() {}

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static LocalDateTime parse(String dateTime) {
        if (dateTime == null || dateTime.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DEFAULT_FORMATTER);
    }

    public static long elapsedMillis(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000L;
    }

}
