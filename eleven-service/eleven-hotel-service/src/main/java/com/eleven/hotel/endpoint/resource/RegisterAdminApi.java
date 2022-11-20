package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.RegisterReviewRequest;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.service.RegisterService;
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
@Tag(name = HotelEndpoints.Tags.REGISTER)
@RequestMapping(HotelEndpoints.Paths.REGISTER)
public class RegisterAdminApi {

    private final RegisterService registerService;

    @Operation(summary = "review the register")
    @PostMapping("/{registerId}/reviews")
    public void reviewRegister(@PathVariable("registerId") String registerId, @RequestBody @Validated RegisterReviewRequest request) {
        var command = RegisterReviewCommand.builder()
                .registerId(registerId)
                .pass(request.getPass())
                .build();
        registerService.review(command);
    }


}
