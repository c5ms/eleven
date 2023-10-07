package com.eleven.doney.domain;

import com.eleven.core.domain.DomainRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends DomainRepository<Member, String> {
    @Modifying
    @Query("delete from doney_member where user_id=:userId")
    long deleteAllByUserId(@Param("userId") String userId);

    @Modifying
    @Query("delete from doney_member where project_id=:projectId")
    long deleteAllByProjectId(@Param("projectId")String projectId);

    boolean existsByProjectIdAndUserId(String projectId, String userId);

    List<Member> findByProjectId(String projectId, Sort sort);
}
