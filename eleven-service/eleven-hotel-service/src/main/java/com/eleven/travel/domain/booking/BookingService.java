package com.eleven.travel.domain.booking;

import com.eleven.travel.domain.booking.command.BookingCreateCommand;
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
////        var hotel = hotelReader.readHotel(command.getHotelId()).orElseThrow(BookingErrors.HOTEL_NOT_EXIST::toException);
//        var plan = planReader.readPlan(command.getHotelId(), command.getPlanId(), command.getRoomId()).orElseThrow(BookingErrors.PLAN_NOT_FOUND::toException);
//
//        var booking = new Booking(plan, command.getPersonCount(), command.getSaleChannel(), command.getStayPeriod());
//        bookingRepository.persist(booking);
//        return booking;
        return null;
    }

}
