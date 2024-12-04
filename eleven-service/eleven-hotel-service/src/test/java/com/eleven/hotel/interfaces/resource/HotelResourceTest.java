package com.eleven.hotel.interfaces.resource;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.api.interfaces.model.hotel.HotelDto;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.interfaces.converter.HotelConvertor;
import com.eleven.hotel.interfaces.utils.MvcTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @ParameterizedTest
    @ValueSource(strings = "requests/hotel/createHotel_01.json")
    void createHotel(String filename) throws Exception {
        var request = MvcTestUtils.withContent(post("/hotels"), filename);
        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void queryHotel() throws Exception {
        var page = new PageImpl<Hotel>(new ArrayList<>());
        Mockito.when(hotelQuery.queryPage(Mockito.any(), Mockito.any())).thenReturn(page);
        this.mockMvc.perform(get("/hotels")
                .queryParam("hotelName", "hotelName")
                .queryParam("page", "1")
                .queryParam("size", "20")
        ).andExpect(status().isOk());
    }

    @Test
    void readHotel_ok() throws Exception {
        var hotel = Mockito.mock(Hotel.class);
        var hotelDto = Mockito.mock(HotelDto.class);
        Mockito.when(hotelQuery.read(Mockito.anyLong())).thenReturn(Optional.of(hotel));
        Mockito.when(hotelConvertor.toDto(hotel)).thenReturn(hotelDto);
        this.mockMvc.perform(get("/hotels/{hotelId}", 1)).andExpect(status().isOk());
    }

    @Test
    void readHotel_notfound() throws Exception {
        Mockito.when(hotelQuery.read(Mockito.anyLong())).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/hotels/{hotelId}", 1)).andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = "requests/hotel/updateHotel_01.json")
    void updateHotel_ok(String filename) throws Exception {
        var request = MvcTestUtils.withContent(post("/hotels/{hotelId}", 1), filename);
        this.mockMvc.perform(request).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = "requests/hotel/updateHotel_01.json")
    void updateHotel_notfound(String filename) throws Exception {
        Mockito.when(hotelService.update(Mockito.anyLong(),Mockito.any())).thenThrow(NoPrincipalException.class);
        var request = MvcTestUtils.withContent(post("/hotels/{hotelId}", 1), filename);
        this.mockMvc.perform(request).andExpect(status().isNotFound());
    }

}
