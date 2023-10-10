package com.eleven.upms.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {

    @Modifying
    @Query("update upms_access_token set expire_at=:expireAt where principal_name=:#{[0].name} and principal_type=:#{[0].type}")
    long expireAllByOwner(@Param("owner") AccessToken.Owner owner, @Param("expireAt") LocalDateTime expireAt);

    @Query("select * from upms_access_token where expire_at >:expireAt")
    List<AccessToken> findValidToken(LocalDateTime expireAt);

    @Query("select * from upms_access_token where principal_name=:#{[0].name} and principal_type=:#{[0].type} and expire_at >:expireAt")
    List<AccessToken> findValidTokenByOwner(@Param("owner") AccessToken.Owner owner, @Param("expireAt") LocalDateTime expireAt);
}
