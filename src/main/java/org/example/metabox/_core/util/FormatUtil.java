package org.example.metabox._core.util;

import org.springframework.cglib.core.Local;

import java.text.NumberFormat;
import java.time.LocalDate;
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
}
