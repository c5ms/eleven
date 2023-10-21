package com.eleven.doney.domain;

import com.eleven.core.domain.DomainRepository;
import com.eleven.doney.domain.MemberRole;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRoleRepository  extends DomainRepository<MemberRole, String> {

    @Modifying
    @Query("delete from doney_member_role where user_id=:userId")
    long deleteAllByUserId(@Param("userId") String userId);

    @Modifying
    @Query("delete from doney_member_role where project_id=:projectId")
    long deleteAllByProjectId(@Param("projectId") String projectId);

}
