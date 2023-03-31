package com.demcia.eleven.domain.helper;

import com.demcia.eleven.core.pageable.Pagination;
import com.demcia.eleven.core.pageable.PaginationResult;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableQueryHelper {

    public static Pageable toSpringDataPageable(Pagination queryAction, Sort sort) {
        return PageRequest.of(queryAction.getPage() - 1, queryAction.getSize(), sort);
    }

    public static Pageable toSpringDataPageable(Pagination queryAction) {
        return toSpringDataPageable(queryAction, Sort.unsorted());
    }

    public static <T> PaginationResult<T> toPageResult(Page<T> page) {
        return PaginationResult.of(page.getContent())
                .withSize(page.getSize())
                .withTotalSize(page.getTotalElements());
    }
}
