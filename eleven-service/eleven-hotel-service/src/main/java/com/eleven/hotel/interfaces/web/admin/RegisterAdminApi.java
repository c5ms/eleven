package com.eleven.hotel.interfaces.web.admin;

import com.eleven.hotel.api.interfaces.request.RegisterReviewRequest;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.service.RegisterService;
import com.eleven.hotel.interfaces.support.AsAdminApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AsAdminApi
@RequiredArgsConstructor
@Tag(name = "register")
@RequestMapping("/registers")
public class RegisterAdminApi {

    private final RegisterService registerService;

    @Operation(summary = "review the register")
    @PostMapping("/{registerId:[0-9]+}/reviews")
    public void reviewRegister(@PathVariable("registerId") Long registerId, @RequestBody @Validated RegisterReviewRequest request) {
        var command = RegisterReviewCommand.builder()
            .pass(request.getPass())
            .build();
        registerService.review(registerId, command);
    }

}
