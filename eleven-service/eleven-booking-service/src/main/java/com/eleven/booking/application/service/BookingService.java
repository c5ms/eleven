package com.eleven.booking.application.service;

import com.eleven.booking.api.domain.errors.BookingErrors;
import com.eleven.booking.application.command.BookingCreateCommand;
import com.eleven.booking.domain.model.booking.Booking;
import com.eleven.booking.domain.model.booking.BookingRepository;
import com.eleven.core.domain.DomainValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final PlanReader planReader;
    private final HotelReader hotelReader;

    private final BookingRepository bookingRepository;

    @Transactional(rollbackFor = Exception.class)
    public Booking createBooking(BookingCreateCommand command) {
//        var hotel = hotelReader.readHotel(command.getHotelId()).orElseThrow(BookingErrors.HOTEL_NOT_EXIST::toException);
        var plan = planReader.readPlan(command.getHotelId(), command.getPlanId(), command.getRoomId()).orElseThrow(BookingErrors.PLAN_NOT_FOUND::toException);
        DomainValidator.must(plan.isOnSale(), BookingErrors.PLAN_NOT_ON_SALE);
        DomainValidator.must(plan.isOnSale(command.getRoomId()), BookingErrors.ROOM_NOT_ON_SALE);

        var booking = new Booking(plan, command.getPersonCount(), command.getSaleChannel(), command.getStayPeriod());
        bookingRepository.persist(booking);
        return booking;
    }

}
