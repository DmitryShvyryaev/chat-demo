package ru.chatdemo.util;

import org.springframework.core.NestedExceptionUtils;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static Throwable getRootCause(Throwable e) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(e);
        return rootCause == null ? e : rootCause;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}
