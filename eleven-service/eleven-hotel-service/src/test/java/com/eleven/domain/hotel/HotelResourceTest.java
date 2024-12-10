package com.eleven.domain.hotel;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.core.configure.ElevenCoreConfigure;
import com.eleven.core.configure.EnableElevenSecurity;
import com.eleven.domain.hotel.command.HotelCreateCommand;
import com.eleven.domain.hotel.command.HotelUpdateCommand;
import com.eleven.domain.hotel.request.HotelCreateRequest;
import com.eleven.domain.hotel.request.HotelUpdateRequest;
import com.eleven.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Import(ElevenCoreConfigure.class)
@EnableElevenSecurity
@WebMvcTest({HotelResource.class,HotelConvertor.class})
class HotelResourceTest {

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    HotelFinder hotelFinder;

    @MockitoBean
    HotelService hotelService;

    @MockitoBean
    HotelConvertor hotelConvertor;

    @ParameterizedTest
    @ValueSource(strings = "/requests/hotel/createHotel_01.json")
    void createHotel_200(String filename) {
        given(hotelConvertor.toCommand(any(HotelCreateRequest.class))).willCallRealMethod();
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.create(any(HotelCreateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
                .uri("/hotels")
                .content(TestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatusOk()
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void queryHotel_200() {
        var page = new PageImpl<Hotel>(new ArrayList<>());
        given(hotelFinder.queryPage(any(), any())).willReturn(page);

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
        given(hotelFinder.read(Mockito.anyLong())).willReturn(Optional.of(mock(Hotel.class)));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));

        this.mvc.get()
                .uri("/hotels/{hotelId}", 1)
                .assertThat()
                .hasStatusOk()
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readHotel_404() {
        given(hotelFinder.read(Mockito.anyLong())).willReturn(Optional.empty());
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
                .content(TestUtils.loadJson(filename))
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
                .content(TestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

}
