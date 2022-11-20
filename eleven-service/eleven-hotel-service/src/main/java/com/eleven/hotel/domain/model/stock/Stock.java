package com.eleven.hotel.domain.model.stock;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.mapping.Table;

@Slf4j
@Table(name = "stock")
@Getter
@FieldNameConstants
public class Stock {

}
