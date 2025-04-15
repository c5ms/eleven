package com.eleven.travel.component.plan;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobStage {

	private Job job;
	private LocalDate startData;
	private LocalDate endDate;

	private JobStage(Job job, LocalDate startData, Duration duration) {
		this.job = job;
		this.startData = startData;
		this.endDate = this.startData.plusDays(duration.toDays());
	}

	static JobStage of(Job job, LocalDate startData, Duration duration) {
		return new JobStage(job, startData, duration);
	}

	void replaceStage(Job job) {
		this.job = job;
	}

	void forward(Duration duration) {
		this.startData = this.startData.plusDays(duration.toDays());
		this.endDate = this.endDate.plusDays(duration.toDays());
	}

	void backward(Duration duration) {
		this.startData = this.startData.minusDays(duration.toDays());
		this.endDate = this.endDate.minusDays(duration.toDays());
	}

	void forwardDuration(Duration duration) {
		this.endDate = this.startData.plusDays(duration.toDays());
	}

	void backwardDuration(Duration duration) {
		this.startData = this.endDate.minusDays(duration.toDays());
	}

	public Duration getDuration() {
		return Duration.between(this.startData.atStartOfDay(), this.endDate.atStartOfDay());
	}

	public String getStageName() {
		return this.getJob().getName();
	}

	@Override
	public String toString() {
		return job.getName() + "[" + startData + "," + endDate + "]";
	}

}
