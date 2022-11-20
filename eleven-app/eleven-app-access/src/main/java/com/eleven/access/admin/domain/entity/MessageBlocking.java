package com.eleven.access.admin.domain.entity;

import com.cnetong.common.domain.AbstractIdDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "message_blocking",
        indexes = {@Index(name = "idx_message_blocking", columnList = "listener_id_,topic_,solve_,solution_")}
)
public class MessageBlocking extends AbstractIdDomain {

    @Schema(description = "监听 ID")
    @Column(name = "listener_id_")
    private String listenerId;

    @Schema(description = "阻塞描述")
    @Column(name = "description_")
    private String description;

    @Schema(description = "阻塞的数据")
    @Column(name = "data_", length = 1024)
    private String data;

    @Schema(description = "主题")
    @Column(name = "topic_", length = 50)
    private String topic;

    @Schema(description = "阻塞是否被解决")
    @Column(name = "solve_")
    private boolean solve = false;

    @Schema(description = "解决方案")
    @Column(name = "solution_")
    private String solution;

    @Schema(description = "创建时间")
    @Column(name = "create_date_")
    private LocalDateTime createDate;

    @Schema(description = "异常信息")
    @Column(name = "exceptionMessage_", length = 1024)
    private String exceptionMessage;

    @Schema(description = "异常信息")
    @Column(name = "exceptionStack_", length = 4096)
    private String exceptionStack;

    public void error(Throwable e) {
        setExceptionMessage(StringUtils.substring(ExceptionUtils.getRootCauseMessage(e), 0, 1024));
        setExceptionStack(StringUtils.substring(ExceptionUtils.getStackTrace(e), 0, 4096));
    }

    public MessageBlocking setData(String data) {
        this.data = StringUtils.substring(data, 0, 1024);
        return this;
    }
}
