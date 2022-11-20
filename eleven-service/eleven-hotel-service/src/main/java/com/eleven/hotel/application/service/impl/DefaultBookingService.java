package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.BookingCommand;
import com.eleven.hotel.application.service.BookingService;
import com.eleven.hotel.domain.model.booking.Booking;
import com.eleven.hotel.domain.model.booking.BookingManager;
import com.eleven.hotel.domain.model.booking.BookingRepository;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.HotelRoomRepository;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.model.traveler.Traveler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DefaultBookingService implements BookingService {

    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final HotelRoomRepository hotelRoomRepository;

    private final BookingManager bookingManager;

    @Override
    public Booking book(BookingCommand bookingCommand) {
        var plan = planRepository.requireById(bookingCommand.getPlanId());
        var hotel = hotelRepository.requireById(plan.getHotelId());
        var room = hotelRoomRepository.requireById(bookingCommand.getRoomId());
        var traveler = new Traveler();
        var bookingId = bookingManager.bookId();

        var booking = new Booking(
                bookingId,
                hotel,
                plan,
                room,
                bookingCommand.getStayPeriod(),
                traveler
        );
        bookingManager.validate(booking);
        bookingRepository.save(booking);
        return booking;
    }

}
