package com.eleven.travel.core.layer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public abstract class Vo implements Serializable {
}
