package org.gitqh.interview.util;

/**
 * Created by gitqh on 05/08/2017.
 */
public class DoubleUtil {
    public static Double tryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
