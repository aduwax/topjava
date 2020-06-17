package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    // temporarily (while servlets in project)
    public static LocalDate parseLocalDateOrNull(String charSequence) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(charSequence);
        } catch (Exception e) {
            return null;
        }
        return localDate;
    }

    // temporarily (while servlets in project)
    public static LocalTime parseLocalTimeOrNull(String charSequence) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(charSequence);
        } catch (Exception e) {
            return null;
        }
        return localTime;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

