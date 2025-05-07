package com.motiveschina.core.layer.support;

import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractDomainEntity implements DomainEntity {

}
