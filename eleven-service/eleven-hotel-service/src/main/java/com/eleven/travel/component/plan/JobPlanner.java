package com.eleven.travel.component.plan;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class JobPlanner {

    private final LocalDate initialStartDate;
    private final List<JobStage> jobStages = new ArrayList<>();
    private final Map<String, Integer> stageIndexMap = new HashMap<>();

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
        this.stageIndexMap.put(job.getName(), jobStages.size() - 1);
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
        return getLast().map(JobStage::getEndDate).orElse(initialStartDate);
    }

    public LocalDate getStartDate() {
        return getFirst().map(JobStage::getStartDate).orElse(initialStartDate);
    }

    private Optional<JobStage> getFirst() {
        return jobStages.isEmpty() ? Optional.empty() : Optional.of(jobStages.get(0));
    }

    private Optional<JobStage> getLast() {
        return jobStages.isEmpty() ? Optional.empty() : Optional.of(jobStages.get(jobStages.size() - 1));
    }

    public Optional<JobStage> find(String name) {
        return this.jobStages.stream()
                .filter(jobStage -> StringUtils.equals(jobStage.getStageName(), name))
                .findFirst();
    }

    private List<JobStage> after(String name) {
        int index = indexOf(name);
        return index != -1 && index < jobStages.size() - 1 ? jobStages.subList(index + 1, jobStages.size()) : new ArrayList<>();
    }

    private List<JobStage> before(String stageName) {
        int index = indexOf(stageName);
        return index > 0 ? jobStages.subList(0, index) : new ArrayList<>();
    }

    private boolean contains(String name) {
        return indexOf(name) != -1;
    }

    private int indexOf(String name) {
        return stageIndexMap.getOrDefault(name, -1);
    }
}

