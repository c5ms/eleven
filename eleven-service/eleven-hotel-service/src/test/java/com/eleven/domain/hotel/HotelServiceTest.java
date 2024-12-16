package com.eleven.domain.hotel;

import com.eleven.base.AsServiceTest;
import com.eleven.framework.authorize.NoPrincipalException;
import com.eleven.domain.hotel.command.HotelCreateCommand;
import com.eleven.domain.hotel.command.HotelUpdateCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    }

    @Test
    void update() {
        var hotel = mock(Hotel.class);
        var command = mock(HotelUpdateCommand.class);
        given(hotelRepository.findById(1L)).willReturn(Optional.of(hotel));

        hotelService.update(1L, command);

        then(hotel).should(times(1)).update(any(HotelPatch.class));
        then(hotelManager).should(times(1)).validate(any(Hotel.class));
    }

    @Test
    void update_throw_NoPrincipalException() {
        given(hotelRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.update(1L, mock(HotelUpdateCommand.class))).isInstanceOf(NoPrincipalException.class);
    }

    @Test
    void open() {
        var hotel = mock(Hotel.class);
        given(hotelRepository.findById(1L)).willReturn(Optional.of(hotel));

        hotelService.open(1L);

        then(hotel).should(times(1)).active();
    }

    @Test
    void close() {
        var hotel = mock(Hotel.class);
        given(hotelRepository.findById(1L)).willReturn(Optional.of(hotel));

        hotelService.close(1L);

        then(hotel).should(times(1)).deactivate();

    }
}
