package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.application.query.PageResult;
import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.hotel.api.application.event.HotelUpdatedEvent;
import com.eleven.hotel.application.command.HotelCloseCommand;
import com.eleven.hotel.application.command.HotelOpenCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.relational.core.query.Criteria.empty;
import static org.springframework.data.relational.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HotelService {

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;
    private final QuerySupport querySupport;

    @Transactional(readOnly = true)
    public Optional<Hotel> read(String hotelId) {
        return hotelRepository.findById(hotelId);
    }

    @Transactional(readOnly = true)
    public PageResult<Hotel> query(HotelQuery query) {
        var criteria = empty();

        if (StringUtils.isNotBlank(query.getHotelName())) {
            criteria = criteria.and(where(String.join(Hotel.Fields.description, Hotel.Description.Fields.name)).like(query.getHotelName()+"%"));
        }

        var sort = Sort.by(String.join(Hotel.Fields.audition, Audition.Fields.createAt)).descending();
        return querySupport.query(Hotel.class, criteria,  query,sort);
    }

    public void update(HotelUpdateCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());

        Optional.ofNullable(command.getPosition()).ifPresent(hotel::relocate);
        Optional.ofNullable(command.getDescription()).ifPresent(hotel::setDescription);

        hotelManager.validate(hotel);
        hotelRepository.save(hotel);
        ApplicationHelper.publishEvent(new HotelUpdatedEvent(hotel.getId()));
    }

    public void open(HotelOpenCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        hotel.startSale();
        hotelRepository.save(hotel);
    }

    public void close(HotelCloseCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        hotel.stopSale();
        hotelRepository.save(hotel);
    }



}
