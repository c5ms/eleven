package com.eleven.upms.domain.model;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface AuthorityRepository extends CrudRepository<Authority, String> {

    Collection<Authority> findByOwner(Authority.Owner owner);

    @Modifying
    @Query("delete from upms_authority " +
           "where owner_type=:ownerType " +
           "and owner_name=:ownerName " +
           "and power_type=:powerType")
    void deleteByOwnerAndPowerType(@Param("ownerType") String ownerType,
                                   @Param("ownerName") String ownerName,
                                   @Param("powerType") String powerType);


    @Modifying
    @Query("delete from upms_authority " +
           "where power_name=:powerName " +
           "and power_type=:powerType")
    void deleteByPower(@Param("powerType") String powerType,
                       @Param("powerName") String powerName);

    @Query("select * from upms_authority " +
           "where owner_type=:ownerType " +
           "and owner_name=:ownerName " +
           "and power_type in (:powerType)")
    Collection<Authority> findByOwner(@Param("ownerType") String ownerType,
                                      @Param("ownerName") String ownerName,
                                      @Param("powerType") Collection<String> powerType);


    @Modifying
    @Query("delete from upms_authority " +
           "where owner_type=:ownerType " +
           "and owner_name=:ownerName ")
    void deleteByOwner(@Param("ownerType") String ownerType,
                       @Param("ownerName") String ownerName);

}
