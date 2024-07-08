package com.gamemoonchul.common.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class StringUtils {
    public static String getTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        if (duration.isNegative() || duration.isZero()) {
            return "방금 전";
        }

        long days = duration.toDays();
        if (days > 0) {
            return days + "일 전";
        }

        long hours = duration.toHours();
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = duration.toMinutes();
        if (minutes > 0) {
            return minutes + "분 전";
        }

        long seconds = duration.getSeconds();
        if (seconds > 0) {
            return seconds + "초 전";
        }

        return "방금 전";
    }

}
