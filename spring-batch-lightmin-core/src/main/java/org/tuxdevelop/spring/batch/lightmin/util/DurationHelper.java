package org.tuxdevelop.spring.batch.lightmin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Marcel Becker
 * @since 0.1
 * <p>
 * Utility class to generate human readable durations
 * </p>
 */
@Slf4j
public final class DurationHelper {

    private DurationHelper() {
    }

    private static final long MILLIS_UPPER_BOUND = 1000;
    private static final long SECONDS_UPPER_BOUND = 60000;
    private static final long MINUTES_UPPER_BOUND = 3600000;

    private static final String MILLIS_FORMAT = "SSS";
    private static final String SECONDS_MILLIS_FORMAT = "ss:SSS";
    private static final String MINUTES_SECONDS_MILLIS_FORMAT = "mm:ss:SSS";
    private static final String HOURS_MINUTES_SECONDS_MILLIS_FORMAT = "hh:mm:ss:SSS";

    /**
     * Creates a human readable String of duration between a start date and an end date
     *
     * @param startTime beginning of the duration interval
     * @param endTime   end of the duration interval
     * @return a {@link java.lang.String} representation of the duration
     */
    public static String createDurationValue(Date startTime, Date endTime) {
        final Date current = new Date();
        if (startTime == null) {
            startTime = current;
            log.info("startTime was null, set to current date");
        }
        if (endTime == null) {
            endTime = current;
            log.info("endTime was null, set to current date");
        }
        Long duration = endTime.getTime() - startTime.getTime();
        if (duration < 0) {
            //throw new IllegalArgumentException("The duration may not be negative! Values [startTime:" + startTime
            //        + "], [endTime:" + endTime + "], [duration:" + duration + "]");
            duration = 0L;
        }
        return format(new Date(duration));
    }

    private static String format(final Date date) {
        final long duration = date.getTime();
        final SimpleDateFormat simpleDateFormat;
        if (duration < MILLIS_UPPER_BOUND) {
            simpleDateFormat = new SimpleDateFormat(MILLIS_FORMAT);
        } else if (duration < SECONDS_UPPER_BOUND) {
            simpleDateFormat = new SimpleDateFormat(SECONDS_MILLIS_FORMAT);
        } else if (duration < MINUTES_UPPER_BOUND) {
            simpleDateFormat = new SimpleDateFormat(MINUTES_SECONDS_MILLIS_FORMAT);
        } else {
            simpleDateFormat = new SimpleDateFormat(HOURS_MINUTES_SECONDS_MILLIS_FORMAT);
        }
        return simpleDateFormat.format(date);
    }
}
