package com.motiveschina.core.utils.plan.support;

import com.motiveschina.core.utils.plan.Job;
import lombok.Getter;

@Getter
public class SimpleJob implements Job {

    private final String name;

    private SimpleJob(String name) {
        this.name = name;
    }

    public static SimpleJob of(String name) {
        return new SimpleJob(name);
    }
}
