package com.eleven.access.admin.domain.action;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageBlockingSolveAction implements Serializable {
    private String blockingId;
    private String solution;
}
