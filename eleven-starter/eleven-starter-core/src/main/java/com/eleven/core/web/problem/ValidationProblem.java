package com.eleven.core.web.problem;

import com.eleven.core.constants.ElevenConstants;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(chain = true)
public class ValidationProblem extends Problem {

    private final List<Field> fields = new ArrayList<>();

    public ValidationProblem() {
        super(
                String.format("%s:%s", ElevenConstants.ERROR_VALIDATE_FAILED.getDomain(), ElevenConstants.ERROR_VALIDATE_FAILED.getError()),
                ElevenConstants.ERROR_VALIDATE_FAILED.getMessage()
        );
    }

    public static ValidationProblem empty() {
        return new ValidationProblem();
    }

    public ValidationProblem addField(String field, String error, String message){
        this.fields.add(new Field(field,error,message));
        return this;
    }

    public ValidationProblem addField(Field field){
        this.fields.add(field);
        return this;
    }

    @Value
    public static class Field {
          String field;
          String error;
          String message;
    }
}
