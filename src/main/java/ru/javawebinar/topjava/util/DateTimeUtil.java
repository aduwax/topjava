package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenHalfOpenDateTime(LocalDateTime ldt, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ldt.compareTo(startDateTime) >= 0 && ldt.compareTo(endDateTime) < 0;
    }

    public static LocalDateTime parse(String localDate, String localTime, LocalDateTime defaultValue) {
        return LocalDateTime.of(
                LocalDate.parse(localDate != null && !localDate.isEmpty()
                        ? localDate
                        : defaultValue.toLocalDate().toString()),
                LocalTime.parse(localTime != null && !localTime.isEmpty()
                        ? localTime
                        : defaultValue.toLocalTime().toString()
                )
        );
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

