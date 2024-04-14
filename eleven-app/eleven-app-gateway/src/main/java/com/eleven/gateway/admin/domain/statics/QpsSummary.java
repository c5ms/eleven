package com.eleven.gateway.admin.domain.statics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class QpsSummary {

    LocalDateTime statTime;

    double qps;

    double avgQps;

}
