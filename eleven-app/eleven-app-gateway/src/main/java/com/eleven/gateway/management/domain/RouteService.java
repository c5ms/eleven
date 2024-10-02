package com.eleven.gateway.management.domain;

import com.eleven.core.data.AbstractAuditEntity;
import com.eleven.core.data.DomainSupport;
import com.eleven.core.domain.PaginationResult;
import com.eleven.gateway.management.model.RouteCreateAction;
import com.eleven.gateway.management.model.RouteDto;
import com.eleven.gateway.management.model.RouteQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final DomainSupport domainSupport;
    private final RouteConvertor routeConvertor;
    private final RouteRepository routeRepository;

    @Transactional(readOnly = true)
    public PaginationResult<RouteDto> queryRoutePage(RouteQuery filter) {
        var criteria = Criteria.empty();
        var query = Query.query(criteria).sort(Sort.by(AbstractAuditEntity.Fields.createAt).descending());
        return domainSupport.queryPage(query, Route.class, filter.getPage(), filter.getSize())
            .map(routeConvertor::toDto);
    }

    @Transactional(rollbackFor = Exception.class)
    public RouteDto createRoute(RouteCreateAction action) {
        var id = domainSupport.getNextId();
        var route = new Route(id, action);
        routeRepository.save(route);
        return routeConvertor.toDto(route);
    }
}
