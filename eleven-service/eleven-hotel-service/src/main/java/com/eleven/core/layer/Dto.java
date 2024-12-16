package com.eleven.core.layer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class Dto implements Serializable {

}
