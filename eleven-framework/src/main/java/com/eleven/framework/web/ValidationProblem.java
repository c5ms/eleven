package com.eleven.framework.web;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(chain = true)
public class ValidationProblem extends Problem {

    private final List<Field> fields = new ArrayList<>();

    public ValidationProblem() {
        super("validate_failed", "Validate failed");
    }

    public static ValidationProblem empty() {
        return new ValidationProblem();
    }

    public ValidationProblem addField(String field, String error, String message) {
        this.fields.add(new Field(field, error, message));
        return this;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    @Hidden
    public record Field(String field, String error, String message) {
    }
}
