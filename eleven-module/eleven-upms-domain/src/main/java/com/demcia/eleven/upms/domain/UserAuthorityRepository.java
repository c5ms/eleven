package com.demcia.eleven.upms.domain;

import cn.hutool.log.Log;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, String> {


    @Modifying
    @Query("""
            delete from upms_user_authority
            where user_id_=:user
              and type_=:type
              and name_=:name
            """)
    void deleteByUserAndAuthority(@Param("user") String user,
                                 @Param("type") String type,
                                 @Param("name") String name);

    @Query("""
            select * from upms_user_authority
            where user_id_=:user
            """)
    Collection<UserAuthority> findByUser(@Param("user") String user);

}