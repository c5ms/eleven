package com.eleven.gateway.management.model;

import com.eleven.core.domain.PaginationQuery;
import com.eleven.upms.model.UserState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class RouteQuery extends PaginationQuery {


}
