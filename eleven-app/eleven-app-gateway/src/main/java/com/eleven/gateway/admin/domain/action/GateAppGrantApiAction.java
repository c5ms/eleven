package com.eleven.gateway.admin.domain.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GateAppGrantApiAction implements Serializable {

    @Schema(description = "API ID")
    private List<String> apis=new ArrayList<>();

}
