package com.eleven.gateway.admin.domain.entity;

import com.cnetong.common.domain.AbstractDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
@FieldNameConstants
public class AbstractExceptionableDomain extends AbstractDomain {

    @Column(name = "error_", length = 2000)
    private String error;

    public void error(Throwable e) {
        setError(StringUtils.substring(ExceptionUtils.getRootCauseMessage(e), 0, 2000));
    }

    public void clearError() {
        setError(null);
    }

}
