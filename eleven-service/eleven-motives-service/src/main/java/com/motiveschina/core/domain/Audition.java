package com.motiveschina.core.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Audition {

    @CreatedBy
    @Column(name = "ad_create_by")
    private String createBy;

    @CreatedDate
    @Column(name = "ad_create_at")
    private LocalDateTime createAt;

    @LastModifiedBy
    @Column(name = "ad_update_by")
    private String updateBy;

    @LastModifiedDate
    @Column(name = "ad_update_at")
    private LocalDateTime updateAt;

    public static Audition empty() {
        return new Audition();
    }

}
