package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.BookingCommand;
import com.eleven.hotel.domain.model.booking.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final BookingCharger bookingCharger;
    private final HotelInfoRepository hotelInfoRepository;
    private final PlanInfoRepository planInfoRepository;

    public Booking book(BookingCommand command) {
        var hotel = hotelInfoRepository.findById(command.getHotelId()).orElseThrow();
        var charge = bookingCharger.charge(command.getHotelId(), command.getPlanId(), command.getRoomId(), command.getSaleChannel(), command.getPersonCount());
        var plan = planInfoRepository.findByPlanId(command.getPlanId()).orElseThrow();

        var booking = new Booking(
            1L,
            hotel,
            command.getHotelId(),
            command.getPlanId(),
            command.getPersonCount(),
            command.getStayPeriod(),
            charge.getAmount()
        );

        return booking;
    }

}
