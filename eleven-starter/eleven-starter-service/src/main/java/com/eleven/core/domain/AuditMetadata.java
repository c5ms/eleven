//package com.eleven.core.domain;
//
//import lombok.Data;
//import lombok.experimental.FieldNameConstants;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.relational.core.mapping.Column;
//
//import java.time.LocalDateTime;
//
//
//@Data
//public class AuditMetadata {
//
//    public static final String col_create_by = "create_by";
//    public static final String col_update_by = "update_by";
//    public static final String col_create_at = "create_at";
//    public static final String col_update_at = "update_at";
//
//    @CreatedBy
//    @Column(col_create_by)
//    private String createBy;
//
//    @LastModifiedBy
//    @Column(col_update_by)
//    private String updateBy;
//
//    @CreatedDate
//    @Column(col_create_at)
//    private LocalDateTime createAt;
//
//    @LastModifiedDate
//    @Column(col_update_at)
//    private LocalDateTime updateAt;
//
//}
