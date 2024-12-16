package com.eleven.framework.event;

import com.eleven.framework.security.AuthenticContext;
import com.eleven.framework.security.Principal;
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
    private LocalDateTime time = LocalDateTime.now();

    @Nullable
    private Principal trigger = AuthenticContext.getPrincipal().orElse(null);

}
