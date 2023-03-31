package com.demcia.eleven.upms.domain;

import com.demcia.eleven.upms.domain.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByLogin(String login);

    @Query("select role_ from upms_user_role where user_id_=:id")
    Set<String> findUserRole(@Param("id" ) String userId);

}