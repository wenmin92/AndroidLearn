package cc.wenmin92.androidlearn.jdk.format;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

@SuppressLint("SimpleDateFormat")
public class DateFormat {
    public static void dateFormat() {
        // 时区的使用，z,Z,X
        // z, General time zone; Z, RFC 822 time zone; X, ISO 8601 time zone.
        // z: CST, zzzz: China Standard Time
        // X: +08，XX: +0800，XXX: +08:00
        Date date = new Date();
        Timber.i("Format");
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzz").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzz").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX").format(date));
        printFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(date));

        Timber.i("Parse: 2019-04-11T17:01:16.121+08:00");
        String dateStr = "2019-04-11T17:01:16.121+08:00";
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzz").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSzzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzz").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSzzzz").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSXX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX").parse(dateStr));
        printParse("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", () -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(dateStr));
    }

    private static void printFormat(String method, Supplier<String> format) {
        String s;
        try {
            s = format.get();
        } catch (Exception e) {
            s = e.getMessage();
        }
        Timber.d("[Format] %s, %s", method, s);
    }

    private static void printParse(String method, Supplier<Date> parse) {
        String s;
        try {
            s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(parse.get());
        } catch (Exception e) {
            s = e.getMessage();
        }
        Timber.d("[Parse] %s, %s", method, s);
    }

    private interface Supplier<T> {
        T get() throws Exception;
    }
}
