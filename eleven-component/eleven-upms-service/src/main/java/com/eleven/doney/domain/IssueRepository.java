package com.eleven.doney.domain;

import com.eleven.core.domain.DomainRepository;
import com.eleven.doney.domain.Issue;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IssueRepository extends DomainRepository<Issue, String> {

//    @Query("SELECT * FROM doney_task t where t.project_id=:projectId ORDER BY T.create_at DESC")
    List<Issue> findByProjectId(String projectId, Sort sort);

}
