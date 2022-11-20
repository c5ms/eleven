package com.eleven.access.admin.domain.action;

import com.cnetong.access.admin.domain.entity.SyncTask;
import com.cnetong.common.domain.action.JpaPageableQueryAction;
import com.github.wenhao.jpa.Specifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public class SyncTaskQueryAction extends JpaPageableQueryAction<SyncTask> {

    @Schema(description = "计划名")
    private String title;


    @Override
    public Specification<SyncTask> toSpecification() {
        return Specifications.<SyncTask>and()
                .like(StringUtils.isNotBlank(title), SyncTask.Fields.title, "%" + StringUtils.trim(title) + "%")
                .build();
    }
}
