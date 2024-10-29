package com.eleven.core.application.event;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.security.Principal;
import com.eleven.core.security.SecurityContext;
import com.eleven.core.time.TimeHelper;
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
    private LocalDateTime time = TimeHelper.localDateTime();

    @Nullable
    private Principal trigger = SecurityContext.getPrincipal().orElse(null);

    @Nullable
    private String service = ApplicationHelper.getServiceName();

}
