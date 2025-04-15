package com.eleven.travel.component.plan.support;

import com.rakuten.travel.ross.scheduling.Job;
import lombok.Getter;

@Getter
public class SimpleJob implements Job {

	private final String name;

	private SimpleJob(String name) {
		this.name = name;
	}
	public static SimpleJob of(String name){
		return new SimpleJob(name);
	}
}
