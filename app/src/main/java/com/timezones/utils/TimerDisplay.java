package com.timezones.utils;

import android.annotation.SuppressLint;

import java.time.Duration;

public class TimerDisplay {
    @SuppressLint("DefaultLocale")
    public static String getDisplay(long millis) {

        Duration duration = Duration.ofHours(0).
                plusMinutes(0).plusSeconds(millis / 1000 + 1);
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
