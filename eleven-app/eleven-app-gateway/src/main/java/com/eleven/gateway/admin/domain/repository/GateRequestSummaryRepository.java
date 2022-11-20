package com.eleven.gateway.admin.domain.repository;

import com.eleven.gateway.admin.domain.entity.GateRequestSummary;
import com.eleven.gateway.admin.domain.entity.GateRequestSummaryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface GateRequestSummaryRepository extends JpaRepository<GateRequestSummary, String> {

    @Query(value = "select " +
            " COALESCE(sum(e.routeCount),0) as routeCount," +
            " COALESCE(sum(e.errorCount),0) as errorCount," +
            " COALESCE(sum(e.requestCount),0) as requestCount," +
            " e.staticDate" +
            " from GateRequestSummary e" +
            " where e.staticDate=:staticDate" +
            " group by e.staticDate" +
            " order by e.staticDate"
    )
    GateRequestSummaryView summaryStatics(@Param("staticDate") LocalDate staticDate);
}
