package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.HotelCloseCommand;
import com.eleven.hotel.application.command.HotelOpenCommand;
import com.eleven.hotel.application.command.HotelRelocateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;

public interface HotelService {

    void update(HotelUpdateCommand command);

    void relocate(HotelRelocateCommand command);

    void open(HotelOpenCommand command);

    void close(HotelCloseCommand command);


}
