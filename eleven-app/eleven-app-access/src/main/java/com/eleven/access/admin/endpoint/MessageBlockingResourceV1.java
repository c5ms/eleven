package com.eleven.access.admin.endpoint;

import com.cnetong.access.admin.domain.action.BlockingQueryAction;
import com.cnetong.access.admin.domain.action.MessageBlockingSolveAction;
import com.cnetong.access.admin.domain.entity.MessageBlocking;
import com.cnetong.access.admin.service.BlockingService;
import com.cnetong.common.query.domain.Page;
import com.cnetong.common.web.RestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApi
@Tag(name = "阻塞记录")
@PreAuthorize("isAuthenticated()")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message/blocks")
public class MessageBlockingResourceV1 {
    private final BlockingService blockingService;


    @GetMapping("/query")
    @Operation(summary = "查询阻塞")
    public Page<MessageBlocking> queryBlocking(@ParameterObject BlockingQueryAction queryAction) {
        return blockingService.query(queryAction);
    }


    @PostMapping("/solve")
    @Operation(summary = "查询阻塞")
    public void solveBlocking(@RequestBody MessageBlockingSolveAction action) {
        blockingService.solve(action);
    }

}
