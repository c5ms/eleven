
package com.motiveschina.research.resource;

import com.motiveschina.research.planning.shift.ShiftSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestResource {

    private final SolverManager<ShiftSchedule, UUID> solverManager;

    @PostMapping("/solve")
    public void solve() {

    }


}
