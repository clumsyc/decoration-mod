package me.clumsycat.furnitureexpanded.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    public static String convertDaytimeToHHmm(long dayTime) {
        Calendar c = Calendar.getInstance();
        double ftime = (dayTime+6000)%24000;
        double ftd = ftime/1000;
        double mnt = ((ftd-Math.floor(ftd))*60);
        c.set(Calendar.HOUR_OF_DAY, (int) ftd);
        c.set(Calendar.MINUTE, (int) mnt);

        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
        return sdf.format(c.getTime());
    }
}
