package com.eleven.hotel.application.service;

import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.core.model.PageResult;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelQueryService {

    private final QuerySupport querySupport;

    public PageResult<Hotel> queryPage(HotelQuery query) {
        var criteria = Criteria.empty();

        if (StringUtils.isNotBlank(query.getHotelName())) {
            criteria = criteria.and(Criteria.where(Hotel.Fields.name).like(StringUtils.trim(query.getHotelName()) + "%"));
        }
        var q = Query.query(criteria).sort(Sort.by(Hotel.Fields.audition + "." + Audition.Fields.createAt).descending());
        return querySupport.query(q, Hotel.class, query.getPage(), query.getSize());
    }

}
