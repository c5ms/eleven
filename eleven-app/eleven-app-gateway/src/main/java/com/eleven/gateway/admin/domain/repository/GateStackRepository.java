package com.eleven.gateway.admin.domain.repository;

import com.cnetong.common.domain.DomainRepository;
import com.eleven.gateway.admin.domain.entity.GateStack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GateStackRepository extends DomainRepository<GateStack, String> {

    @Query("from GateStack where published=:published order by order asc")
    List<GateStack> findPublishedServers(@Param("published") boolean published);

}
