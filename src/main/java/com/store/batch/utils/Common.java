package com.store.batch.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Common {
    public static String getTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
    }

    public static String getRequestId(){
        return String.valueOf(UUID.randomUUID().getMostSignificantBits()).replace("-","");
    }

}
