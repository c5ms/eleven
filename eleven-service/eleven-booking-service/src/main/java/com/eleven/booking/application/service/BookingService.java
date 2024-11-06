package com.eleven.booking.application.service;

import com.eleven.booking.api.application.error.BookingErrors;
import com.eleven.booking.application.command.BookingCommand;
import com.eleven.booking.domain.model.booking.Booking;
import com.eleven.booking.domain.model.booking.BookingRepository;
import com.eleven.booking.domain.model.booking.HotelReader;
import com.eleven.booking.domain.model.booking.PlanReader;
import com.eleven.core.domain.DomainContext;
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

    public Booking book(BookingCommand command) {
        var hotel = hotelReader.readHotel(command.getHotelId()).orElseThrow(BookingErrors.HOTEL_NOT_EXIST::toException);
        var plan = planReader.readPlan(command.getHotelId(), command.getPlanId()).orElseThrow(BookingErrors.PLAN_NOT_EXIST::toException);

        var booking = new Booking(
                plan,
                command.getRoomId(),
                command.getPersonCount(),
                command.getSaleChannel(),
                command.getStayPeriod()
        );

        DomainContext.must(booking.hasAmount(), BookingErrors.BOOKING_NO_PRICE);
        bookingRepository.persist(booking);
        return booking;
    }

}
