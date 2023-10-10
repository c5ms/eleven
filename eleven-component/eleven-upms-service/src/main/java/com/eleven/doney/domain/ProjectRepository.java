package com.eleven.doney.domain;

import com.eleven.core.domain.DomainRepository;
import com.eleven.doney.model.ProjectSummary;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends DomainRepository<Project, String> {

    @Query("select * from doney_project where id in (select project_id from doney_member where user_id=:userId)  AND delete_at is null ORDER BY create_at desc ")
    List<Project> findAllByMember(String userId);

    Optional<ProjectSummary> findSummaryById(String id);

    @Query("select count(id) from doney_project where code=:code and delete_at is null")
    boolean existsByCode(@Param("code") String code);
}
