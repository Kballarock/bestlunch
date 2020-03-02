package by.bestlunch.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final LocalTime DEFAULT_EXPIRED_TIME = LocalTime.parse("11:00");
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final LocalDateTime MIN_DATE = LocalDateTime.of(2019, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(2100, 1, 1, 0, 0);

    private DateTimeUtil() {
    }

    public static String toString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static @Nullable
    LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    public static LocalDate getStartInclusive(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE.toLocalDate();
    }

    public static LocalDate getEndInclusive(LocalDate localDate) {
        return localDate != null ? localDate : MAX_DATE.toLocalDate();
    }

    public static LocalDate getCurrentDate(LocalDate localDate) {
        return localDate != null ? localDate : LocalDate.now();
    }
}