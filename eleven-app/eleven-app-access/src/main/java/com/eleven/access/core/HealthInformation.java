package com.eleven.access.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInformation implements Serializable {
    private String id;
    private HealthLevel level;
    private String exception;

    public static HealthInformation healthy(String id) {
        return new HealthInformation(id, HealthLevel.healthy, null);
    }

    public static HealthInformation unhealthy(String id, Exception e) {
        if (e instanceof ResourceBlockingException) {
            return new HealthInformation(id, HealthLevel.blocking, ExceptionUtils.getMessage(e));
        }
        return new HealthInformation(id, HealthLevel.errored, ExceptionUtils.getMessage(e));
    }

    public static HealthInformation unknown(String id) {
        return new HealthInformation(id, HealthLevel.unknown, null);
    }


}
