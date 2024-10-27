package com.eleven.hotel.application.service;

import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.core.application.model.PageResult;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.plan.Plan;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.relational.core.query.Criteria.empty;
import static org.springframework.data.relational.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelQueryService {

    private final QuerySupport querySupport;

    public PageResult<Hotel> queryPage(HotelQuery query) {
        var criteria = empty();

        if (StringUtils.isNotBlank(query.getHotelName())) {
            criteria = criteria.and(where(Hotel.Fields.name).like(startWith(query.getHotelName())));
        }

        var sort = Sort.by(field(Hotel.Fields.audition, Audition.Fields.createAt)).descending();
        var q = Query.query(criteria).sort(sort);
        return querySupport.query(q, Hotel.class, query);
    }

    public PageResult<Plan> queryPage(PlanQuery query) {
        var criteria = empty();

        criteria = criteria.and(where(Plan.Fields.hotelId).is(query.getHotelId()));

        if (StringUtils.isNotBlank(query.getPlanName())) {
            criteria = criteria.and(where(field(Plan.Fields.description, Plan.Description.Fields.name)).like(startWith(query.getPlanName())));
        }

        var sort = Sort.by(field(Hotel.Fields.audition, Audition.Fields.createAt)).descending();
        var q = Query.query(criteria).sort(sort);
        return querySupport.query(q, Plan.class, query);
    }


    private String field(String... props) {
        return String.join(".", props);
    }

    private String startWith(String prefix) {
        return StringUtils.trim(prefix) + "%";
    }

    private String endWith(String prefix) {
        return "%" + StringUtils.trim(prefix);
    }
}
