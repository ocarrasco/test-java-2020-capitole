package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.systemDefault());
    }

    public static Date toUTCDate(LocalDateTime date) {
        return Date.from(date.toInstant(ZoneOffset.UTC));
    }

}
