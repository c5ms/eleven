package com.eleven.core.web.problem;

import com.eleven.core.exception.ProcessError;
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

    public static Problem of(ProcessError error) {
        return new Problem(
                String.format("%s:%s", error.getDomain(), error.getError()),
                error.getMessage()
        );
    }

}
