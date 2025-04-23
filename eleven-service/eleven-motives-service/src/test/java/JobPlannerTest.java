
import com.motiveschina.core.utils.plan.Direction;
import com.motiveschina.core.utils.plan.JobPlanner;
import com.motiveschina.core.utils.plan.support.SimpleJob;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.time.LocalDate;

class JobPlannerTest {

    @org.junit.jupiter.api.Test
    void append() {
        var start = LocalDate.of(2025, 4, 2);
        var scheduling = JobPlanner.from(start);
        Assertions.assertEquals(LocalDate.of(2025, 4, 2), scheduling.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 2), scheduling.getEndDate());

        var stage1 = SimpleJob.of("stage1");
        scheduling.append(stage1, Duration.ofDays(2));

        var stage2 = SimpleJob.of("stage2");
        scheduling.append(stage2, Duration.ofDays(3));

        var stage3 = SimpleJob.of("stage3");
        scheduling.append(stage3, Duration.ofDays(4));

        Assertions.assertEquals(LocalDate.of(2025, 4, 2), scheduling.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 11), scheduling.getEndDate());

        Assertions.assertTrue(scheduling.find("stage1").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 2), scheduling.find("stage1").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 4), scheduling.getJobStages().get(0).getEndDate());

        Assertions.assertTrue(scheduling.find("stage2").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 4), scheduling.find("stage2").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 7), scheduling.find("stage2").get().getEndDate());

        Assertions.assertTrue(scheduling.find("stage3").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 7), scheduling.find("stage3").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 11), scheduling.find("stage3").get().getEndDate());
    }

    @org.junit.jupiter.api.Test
    void reschedule() {
        var now = LocalDate.of(2025, 4, 2);
        var scheduling = JobPlanner.from(now);
        scheduling.reschedule("no_this_stage", Duration.ofDays(10), Direction.Forward);

        var stage1 = SimpleJob.of("stage1");
        scheduling.append(stage1, Duration.ofDays(2));

        var stage2 = SimpleJob.of("stage2");
        scheduling.append(stage2, Duration.ofDays(3));

        var stage3 = SimpleJob.of("stage3");
        scheduling.append(stage3, Duration.ofDays(4));

        scheduling.reschedule("stage2", Duration.ofDays(5), Direction.Forward);
        Assertions.assertEquals(LocalDate.of(2025, 4, 2), scheduling.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 13), scheduling.getEndDate());

        Assertions.assertTrue(scheduling.find("stage2").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 4), scheduling.find("stage2").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 9), scheduling.find("stage2").get().getEndDate());


        scheduling.reschedule("stage3", Duration.ofDays(5), Direction.Backward);
        Assertions.assertEquals(LocalDate.of(2025, 4, 1), scheduling.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 13), scheduling.getEndDate());

        Assertions.assertTrue(scheduling.find("stage3").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 8), scheduling.find("stage3").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 13), scheduling.find("stage3").get().getEndDate());

        scheduling.append(stage3, Duration.ofDays(2));
        Assertions.assertEquals(LocalDate.of(2025, 4, 1), scheduling.getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 10), scheduling.getEndDate());

        Assertions.assertTrue(scheduling.find("stage3").isPresent());
        Assertions.assertEquals(LocalDate.of(2025, 4, 8), scheduling.find("stage3").get().getStartDate());
        Assertions.assertEquals(LocalDate.of(2025, 4, 10), scheduling.find("stage3").get().getEndDate());

    }


}
