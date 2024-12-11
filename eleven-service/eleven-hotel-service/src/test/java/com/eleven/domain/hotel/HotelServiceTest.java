package com.eleven.domain.hotel;

import com.eleven.base.AsServiceTest;
import com.eleven.domain.hotel.command.HotelCreateCommand;
import com.eleven.domain.hotel.command.HotelUpdateCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@AsServiceTest
@SpringBootTest(classes = HotelService.class)
class HotelServiceTest {
    @Autowired
    HotelService hotelService;

    @MockitoBean
    HotelManager hotelManager;

    @MockitoBean
    HotelRepository hotelRepository;

    @Test
    void create() {
        var command = mock(HotelCreateCommand.class);
        hotelService.create(command);

        then(hotelManager).should(times(1)).validate(any(Hotel.class));
        then(hotelRepository).should(times(1)).saveAndFlush(any(Hotel.class));
    }

    @Test
    void update() {
        var hotel=mock(Hotel.class);
        var command = mock(HotelUpdateCommand.class);
        given(hotelRepository.findById(1L)).willReturn(Optional.of(hotel));

        hotelService.update(1L,command);

        then(hotel).should(times(1)).update(any(HotelPatch.class));
        then(hotelManager).should(times(1)).validate(any(Hotel.class));
        then(hotelRepository).should(times(1)).saveAndFlush(any(Hotel.class));
    }
}
