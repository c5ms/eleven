package com.eleven.upms.domain;

import com.eleven.core.data.DomainRepository;
import com.eleven.upms.model.UserSummary;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("select id,nickname,username from upms_user where id =:id")
    Optional<UserSummary> findSummaryById(@Param("id") String id);

}
