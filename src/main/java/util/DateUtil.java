package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String today() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.format(DateTimeFormatter.ISO_DATE);
    }
}
