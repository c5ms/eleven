package com.eleven.core.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListValue<T> {

    private final List<T> values = new ArrayList<>();

    public static <T> ListValue<T> of(List<T> values) {
        var value = new ListValue<T>();
        value.getValues().addAll(values);
        return value;
    }
}
