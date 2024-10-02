package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import com.cnetong.common.persist.jpa.convert.StringStringMapConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽取计划
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@FieldNameConstants
@Table(name = "sync_task_writer")
public class SyncTaskWriter extends AbstractIdDomain {

    @Column(name = "writer_type_")
    private String writerType;

    @Lob
    @Column(name = "writer_config")
    @Convert(converter = StringStringMapConverter.class)
    private Map<String, String> writerConfig = new HashMap<>();

}
