package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.application.constants.HotelConstants;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.values.Address;
import com.eleven.hotel.domain.model.hotel.values.CheckPolicy;
import com.eleven.hotel.domain.model.hotel.values.Position;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@UtilityClass
public class HotelConverter {

}
