package com.eleven.framework.web;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceId {

    private final  String val;

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

    public String asString() {
      return val;
    }
}
