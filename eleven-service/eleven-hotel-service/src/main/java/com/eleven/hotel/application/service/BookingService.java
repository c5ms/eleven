package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.BookingCommand;
import com.eleven.hotel.domain.model.booking.Booking;

public interface BookingService {

    Booking book(BookingCommand bookingCommand);
}
