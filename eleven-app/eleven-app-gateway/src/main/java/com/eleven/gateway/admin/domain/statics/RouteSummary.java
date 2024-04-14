package com.eleven.gateway.admin.domain.statics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.atomic.LongAdder;

@Data
@Accessors(chain = true)
public class RouteSummary {

    private String routeId;
    private String routeName;
    private String stackId;
    private String stackName;

    private LongAdder hits = new LongAdder();
    private LongAdder successes = new LongAdder();
    private LongAdder failures = new LongAdder();
}
