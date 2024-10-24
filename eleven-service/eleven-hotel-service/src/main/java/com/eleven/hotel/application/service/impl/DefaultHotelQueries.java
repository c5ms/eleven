package com.eleven.hotel.application.service.impl;

import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.core.model.PageResult;
import com.eleven.hotel.application.command.HotelQueryFilter;
import com.eleven.hotel.application.service.HotelQueries;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultHotelQueries implements HotelQueries {

    private final QuerySupport querySupport;

    @Override
    public PageResult<Hotel> queryPage(HotelQueryFilter filter) {
        var criteria = Criteria.empty();

        if (StringUtils.isNotBlank(filter.getHotelName())) {
            criteria = criteria.and(Criteria.where(Hotel.Fields.name).like(StringUtils.trim(filter.getHotelName()) + "%"));
        }
        var query = Query.query(criteria).sort(Sort.by(Hotel.Fields.audition + "." + Audition.Fields.createAt).descending());
        return querySupport.query(query, Hotel.class, filter.getPage(), filter.getSize());
    }

}
