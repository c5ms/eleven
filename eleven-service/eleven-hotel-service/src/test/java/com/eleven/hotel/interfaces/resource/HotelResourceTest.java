package com.eleven.hotel.interfaces.resource;

import com.eleven.core.test.http.MvcTestUtils;
import com.eleven.hotel.api.interfaces.model.hotel.HotelDto;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.interfaces.converter.HotelConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HotelQuery hotelQuery;

    @MockBean
    HotelService hotelService;

    @MockBean
    HotelConvertor hotelConvertor;

    @Autowired
    HotelResource hotelResource;

    @ParameterizedTest
    @ValueSource(strings = "requests/hotel/createHotel_01.json")
    void createHotel_ok(String filename) throws Exception {
        var request = MvcTestUtils.doPostJson("/hotels", filename);
        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void queryHotel() {

    }

    @Test
    void readHotel() throws Exception {
        var hotel = Mockito.mock(Hotel.class);
        var hotelDto = Mockito.mock(HotelDto.class);
        Mockito.when(hotelQuery.read(Mockito.anyLong())).thenReturn(Optional.of(hotel));
        Mockito.when(hotelConvertor.toDto(hotel)).thenReturn(hotelDto);
        this.mockMvc.perform(get("/hotels/{hotelId}", 1)).andExpect(status().isOk());
    }


    @Test
    void update() {
    }

    @Test
    void openHotel() {
    }

    @Test
    void closeHotel() {
    }
}
