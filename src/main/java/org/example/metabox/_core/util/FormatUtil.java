package org.example.metabox._core.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    public static String moneyFormat(Long amount) {
        Locale korea = Locale.KOREA;
        NumberFormat koreaFormatter = NumberFormat.getCurrencyInstance(korea);
        return koreaFormatter.format(amount);
    }

    public static String getFormattedPrice(int price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        return numberFormat.format(price);
    }

    public static String viewerFormat(Long viewerCount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);
        return numberFormat.format(viewerCount) + "명";
    }

    public static String timeFormat(LocalDateTime time) {
        // 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        // 포맷팅된 문자열 반환
        return time.format(formatter);
    }
}
