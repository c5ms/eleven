package com.motiveschina.core.utils.plan;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobStage {

	private Job job;
	private LocalDate startDate;
	private LocalDate endDate;

	private JobStage(Job job, LocalDate startDate, Duration duration) {
		this.job = job;
		this.startDate = startDate;
		this.endDate = this.startDate.plusDays(duration.toDays());
	}

	static JobStage of(Job job, LocalDate startData, Duration duration) {
		return new JobStage(job, startData, duration);
	}

	void replaceStage(Job job) {
		this.job = job;
	}

	void forward(Duration duration) {
		this.startDate = this.startDate.plusDays(duration.toDays());
		this.endDate = this.endDate.plusDays(duration.toDays());
	}

	void backward(Duration duration) {
		this.startDate = this.startDate.minusDays(duration.toDays());
		this.endDate = this.endDate.minusDays(duration.toDays());
	}

	void forwardDuration(Duration duration) {
		this.endDate = this.startDate.plusDays(duration.toDays());
	}

	void backwardDuration(Duration duration) {
		this.startDate = this.endDate.minusDays(duration.toDays());
	}

	public Duration getDuration() {
		return Duration.between(this.startDate.atStartOfDay(), this.endDate.atStartOfDay());
	}

	public String getStageName() {
		return this.getJob().getName();
	}

	@Override
	public String toString() {
		return job.getName() + "[" + startDate + "," + endDate + "]";
	}

}
