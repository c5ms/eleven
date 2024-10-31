package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.BookingCommand;
import com.eleven.hotel.domain.model.booking.Booking;
import com.eleven.hotel.domain.manager.BookingManager;
import com.eleven.hotel.domain.model.booking.BookingRepository;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.hotel.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BookingService {

    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    private final BookingManager bookingManager;

    public Booking book(BookingCommand bookingCommand) {
//        var plan = planRepository.requireById(bookingCommand.getPlanId());
//        var hotel = hotelRepository.requireById(plan.getHotelId());
//        var room = roomRepository.requireById(bookingCommand.getRoomId());
//        var traveler = new Traveler();
//        var bookingId = bookingManager.bookId();
//
//        var booking = new Booking(
//            bookingId,
//            hotel,
//            plan,
//            room,
//            bookingCommand.getStayPeriod(),
//            traveler
//        );
//        bookingManager.validate(booking);
//        bookingRepository.save(booking);
//        return booking;
        return null;
    }

}
