package com.eleven.doney.domain;

import com.eleven.core.domain.DomainRepository;
import com.eleven.doney.dto.ProjectSummary;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends DomainRepository<Project, String> {

    Optional<ProjectSummary> findSummaryById(String id);

    @Query("select count(id) from eleven_upms.doney_project where code=:code and delete_at is null")
    boolean existsByCode(@Param("code") String code);
}
