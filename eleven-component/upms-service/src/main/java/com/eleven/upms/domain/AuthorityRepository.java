package com.eleven.upms.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface AuthorityRepository extends CrudRepository<Authority, String> {

    @Modifying
    @Query("delete from upms_authority where owner_type=:ownerType and owner_name=:ownerName and power_type=:powerType")
    void deleteByOwnerAndPowerType(@Param("ownerType") String ownerType,
                                   @Param("ownerName") String ownerName,
                                   @Param("powerType") String powerType);

    @Modifying
    @Query("delete from upms_authority where power_name=:powerName and power_type=:powerType")
    void deleteByPower(@Param("powerType") String powerType,
                       @Param("powerName") String powerName );

    Collection<Authority> findByOwner(Authority.Owner owner);

}
