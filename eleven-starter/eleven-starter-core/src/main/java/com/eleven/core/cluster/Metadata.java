package com.eleven.core.cluster;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class Metadata implements Serializable {

    private String name;
    private String value;

    public Metadata() {
    }

    public Metadata(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public boolean isEmpty() {
        return StringUtils.isAnyBlank(name, value);
    }

    public boolean isNotEqualsTo(Metadata metadata) {
        return !isEqualsTo(metadata);
    }

    public boolean isEqualsTo(Metadata metadata) {
        if (null == metadata) {
            return false;
        }
        if (metadata.isEmpty() && this.isEmpty()) {
            return true;
        }

        return StringUtils.equals(metadata.getValue(), this.getValue());
    }
}
