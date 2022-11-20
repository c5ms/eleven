package com.eleven.core.web.problem;

import com.eleven.core.domain.DomainError;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class Problem {
    private String error;
    private String message;

    public Problem(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public static Problem of(DomainError error) {
        return new Problem(error.getError(), error.getMessage());
    }

    public static Problem of(DomainError error, String message) {
        return  new Problem(error.getError(), message);
    }

}
