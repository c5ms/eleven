package com.eleven.upms.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, String> {


    @Modifying
    @Query("""
            delete from upms_user_authority
            where user_id=:user
              and type=:type
              and name=:name
            """)
    void deleteByUserAndAuthority(@Param("user") String user,
                                  @Param("type") String type,
                                  @Param("name") String name);

    @Query("""
            select * from upms_user_authority
            where user_id=:user
            """)
    Collection<UserAuthority> findByUserId(@Param("user") String user);

}
