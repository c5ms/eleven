package com.eleven.travel.component.plan;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class JobPlanner {

	private final LocalDate initialStartDate;
	private final List<JobStage> jobStages = new ArrayList<>();

	public JobPlanner(LocalDate initialStartDate) {
		this.initialStartDate = initialStartDate;
	}

	public static JobPlanner from(LocalDate initialStartDate) {
		return new JobPlanner(initialStartDate);
	}

	public void append(Job job, Duration duration) {
		if (this.contains(job.getName())) {
			this.find(job.getName()).ifPresent(jobStage -> {
				jobStage.replaceStage(job);
				this.reschedule(jobStage, duration, Direction.Forward);
			});
			return;
		}

		var startDate = getEndDate();
		var unit = JobStage.of(job, startDate, duration);
		this.jobStages.add(unit);
	}

	public void reschedule(String stageName, Duration duration, Direction direction) {
		this.find(stageName).ifPresent(jobStage -> this.reschedule(jobStage, duration, direction));
	}

	private void reschedule(JobStage jobStage, Duration duration, Direction direction) {
		var diff = jobStage.getDuration().minus(duration).abs();

		if (direction == Direction.Forward) {
			jobStage.forwardDuration(duration);
			this.after(jobStage.getStageName()).forEach(jobStage1 -> jobStage1.forward(diff));
		}

		if (direction == Direction.Backward) {
			jobStage.backwardDuration(duration);
			this.before(jobStage.getStageName()).forEach(jobStage1 -> jobStage1.backward(diff));
		}
	}

	public LocalDate getEndDate() {
		return this.getLast()
				.map(JobStage::getEndDate)
				.orElse(initialStartDate);
	}

	public LocalDate getStartDate() {
		return this.getFirst()
				.map(JobStage::getStartData)
				.orElse(initialStartDate);
	}

	private Optional<JobStage> getFirst() {
		if (this.jobStages.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(this.jobStages.get(0));
	}

	private Optional<JobStage> getLast() {
		if (this.jobStages.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(this.jobStages.get(this.jobStages.size() - 1));
	}

	private Optional<JobStage> find(String name) {
		return this.jobStages.stream()
				.filter(jobStage -> StringUtils.equals(jobStage.getStageName(), name))
				.findFirst();
	}

	private List<JobStage> after(String name) {
		return this.jobStages.subList(indexOf(name) + 1, this.jobStages.size());
	}

	private List<JobStage> before(String stageName) {

		return this.jobStages.subList(0, indexOf(stageName));
	}

	private boolean contains(String name) {
		return this.indexOf(name) != -1;
	}

	private int indexOf(String name) {
		for (int i = 0; i < this.jobStages.size(); i++) {
			if (StringUtils.equals(this.jobStages.get(i).getStageName(), name)) {
				return i;
			}
		}
		return -1;
	}
}
