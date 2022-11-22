package com.demcia.eleven.core.helper;

import com.demcia.eleven.core.pageable.PageResult;
import com.demcia.eleven.core.pageable.PageableQueryAction;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableQueryHelper {


    public static Pageable toSpringDataPageable(PageableQueryAction queryAction,Sort sort) {
        return PageRequest.of(queryAction.getPage() - 1, queryAction.getSize(),sort);
    }

    public static Pageable toSpringDataPageable(PageableQueryAction queryAction) {
        return toSpringDataPageable(queryAction,Sort.unsorted());
    }

    public static <T> PageResult<T> toPageResult(org.springframework.data.domain.Page<T> page) {
        return PageResult.of(page.getContent())
                .withSize(page.getSize())
                .withTotalSize(page.getTotalElements());
    }
}
