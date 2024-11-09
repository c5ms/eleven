package com.eleven.core.application.event;

import com.eleven.core.authenticate.Principal;
import com.eleven.core.authenticate.AuthenticContext;
import com.eleven.core.time.TimeContext;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ApplicationEventHeader {

    @Nonnull
    private transient ApplicationEventOrigin from = ApplicationEventOrigin.INTERNAL;

    @Nonnull
    private LocalDateTime time = TimeContext.localDateTime();

    @Nullable
    private Principal trigger = AuthenticContext.getPrincipal().orElse(null);

}
