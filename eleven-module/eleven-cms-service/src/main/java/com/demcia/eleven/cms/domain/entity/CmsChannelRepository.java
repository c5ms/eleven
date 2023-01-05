package com.demcia.eleven.cms.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmsChannelRepository extends JpaRepository<CmsChannel, String>, JpaSpecificationExecutor<CmsChannel> {
}