package com.eleven.access.admin.domain.dto;

import com.cnetong.access.admin.domain.entity.SyncTaskWriter;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * A DTO for the {@link SyncTaskWriter} entity
 */
@Data
public class SyncTaskWriterDto implements Serializable {
    private String writerType;
    private Map<String, String> writerConfig;
}
