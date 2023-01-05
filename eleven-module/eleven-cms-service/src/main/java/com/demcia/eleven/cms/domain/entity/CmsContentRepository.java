package com.demcia.eleven.cms.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmsContentRepository extends JpaRepository<CmsContent, String>, JpaSpecificationExecutor<CmsContent> {
}