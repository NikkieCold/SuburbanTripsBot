package ua.nikkie.SuburbanTripsBot.util;

import static java.util.Objects.isNull;

public class BotUtils {

    public static boolean nonNulls(Object... objects) {
        for (Object object : objects) {
            if (isNull(object)) {
                return false;
            }
        }
        return true;
    }
}
