package com.eleven.gateway.admin.domain.repository;

import com.cnetong.common.domain.DomainRepository;
import com.eleven.gateway.admin.domain.entity.GateApiAccessLog;
import org.springframework.data.repository.query.Param;

public interface GateApiAccessLogRepository  extends DomainRepository<GateApiAccessLog,String> {
	Integer countByApiId(@Param("apiId") String apiId);

	Integer countByAppId(@Param("appId") String appId);
}
