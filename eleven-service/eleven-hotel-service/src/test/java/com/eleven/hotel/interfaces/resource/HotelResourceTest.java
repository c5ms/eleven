package com.eleven.hotel.interfaces.resource;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.core.configure.EnableElevenSecurity;
import com.eleven.hotel.api.interfaces.model.hotel.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.model.hotel.HotelDto;
import com.eleven.hotel.api.interfaces.model.hotel.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.interfaces.converter.HotelConvertor;
import com.eleven.hotel.interfaces.utils.InterfacesTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@WebMvcTest(HotelResource.class)
@EnableElevenSecurity
class HotelResourceTest {

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    HotelQuery hotelQuery;

    @MockitoBean
    HotelService hotelService;

    @MockitoBean
    HotelConvertor hotelConvertor;

    @WithMockUser(roles = "HOTEL")
    @ParameterizedTest
    @ValueSource(strings = "/requests/hotel/createHotel_01.json")
    void createHotel_200(String filename) {
        given(hotelConvertor.toCommand(any(HotelCreateRequest.class))).willReturn(mock(HotelCreateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.create(any(HotelCreateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
                .uri("/hotels")
                .content(InterfacesTestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatusOk()
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void queryHotel_200() {
        var page = new PageImpl<Hotel>(new ArrayList<>());
        given(hotelQuery.queryPage(any(), any())).willReturn(page);

        this.mvc.get()
                .uri("/hotels")
                .queryParam("hotelName", "hotelName")
                .queryParam("page", "1")
                .queryParam("size", "20")
                .assertThat()
                .hasStatusOk()
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readHotel_200() {
        given(hotelQuery.read(Mockito.anyLong())).willReturn(Optional.of(mock(Hotel.class)));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));

        this.mvc.get()
                .uri("/hotels/{hotelId}", 1)
                .assertThat()
                .hasStatusOk()
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readHotel_404() {
        given(hotelQuery.read(Mockito.anyLong())).willReturn(Optional.empty());
        this.mvc.get()
                .uri("/hotels/{hotelId}", 1)
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = "/requests/hotel/updateHotel_01.json")
    void updateHotel_200(String filename) {
        given(hotelConvertor.toCommand(any(HotelUpdateRequest.class))).willReturn(mock(HotelUpdateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.update(any(Long.class), any(HotelUpdateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
                .uri("/hotels/{hotelId}", 1)
                .content(InterfacesTestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = "/requests/hotel/updateHotel_01.json")
    void updateHotel_404(String filename) {
        given(hotelConvertor.toCommand(any(HotelUpdateRequest.class))).willReturn(mock(HotelUpdateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.update(any(Long.class), any(HotelUpdateCommand.class))).willThrow(NoPrincipalException.class);

        this.mvc.post()
                .uri("/hotels/{hotelId}", 1)
                .content(InterfacesTestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

}
