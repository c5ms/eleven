package com.eleven.framework.web;

public record ResourceId(String val) {

    public Long asLong() {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            throw new ResourceIdNotValidException(val, e);
        }
    }

    public Integer asInt() {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new ResourceIdNotValidException(val, e);
        }
    }
}
