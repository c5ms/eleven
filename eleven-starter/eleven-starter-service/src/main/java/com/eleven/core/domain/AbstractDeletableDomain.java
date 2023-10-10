package com.eleven.core.domain;

import com.eleven.core.time.TimeContext;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@FieldNameConstants
public abstract class AbstractDeletableDomain extends AbstractAuditDomain implements Deletable {

    public static final String col_delete_at = "delete_at";

    @Column(col_delete_at)
    private LocalDateTime deleteAt;

    @Override
    public void delete() {
        this.deleteAt = TimeContext.localDateTime();
    }

    public boolean isDeleted() {
        return Objects.nonNull(this.deleteAt);
    }
}
