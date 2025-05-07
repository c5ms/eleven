package com.motiveschina.hotel.features.hotel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Optional;
import com.eleven.framework.domain.NoDomainEntityException;
import com.motiveschina.hotel.features.hotel.command.HotelCreateCommand;
import com.motiveschina.hotel.features.hotel.command.HotelUpdateCommand;
import com.motiveschina.hotel.features.hotel.request.HotelCreateRequest;
import com.motiveschina.hotel.features.hotel.request.HotelUpdateRequest;
import com.motiveschina.hotel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;


@WebMvcTest({HotelResource.class})
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
    @ValueSource(strings = "/hotel/request/200/createHotel_01.json")
    void createHotel_200(String filename) {
        given(hotelConvertor.toCommand(any(HotelCreateRequest.class))).willReturn(mock(HotelCreateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.create(any(HotelCreateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
            .uri("/api/hotels")
            .content(TestUtils.loadJson(filename))
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.OK)
            .hasContentType(MediaType.APPLICATION_JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/400/createHotel_01.json")
    void createHotel_400(String filename) {
        given(hotelConvertor.toCommand(any(HotelCreateRequest.class))).willReturn(mock(HotelCreateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.create(any(HotelCreateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
            .uri("/api/hotels")
            .content(TestUtils.loadJson(filename))
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.BAD_REQUEST)
            .hasContentType(MediaType.APPLICATION_JSON)
        ;

    }

    @Test
    void queryHotel_200() {
        var page = new PageImpl<Hotel>(new ArrayList<>());
        given(hotelFinder.queryPage(any(), any())).willReturn(page);

        this.mvc.get()
            .uri("/api/hotels")
            .queryParam("hotelName", "hotelName")
            .queryParam("page", "1")
            .queryParam("size", "20")
            .assertThat()
            .hasStatus(HttpStatus.OK)
            .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readHotel_200() {
        given(hotelFinder.read(Mockito.anyLong())).willReturn(Optional.of(mock(Hotel.class)));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));

        this.mvc.get()
            .uri("/api/hotels/{hotelId}", 1)
            .assertThat()
            .hasStatus(HttpStatus.OK)
            .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void readHotel_404() {
        given(hotelFinder.read(Mockito.anyLong())).willReturn(Optional.empty());
        this.mvc.get()
            .uri("/api/hotels/{hotelId}", 1)
            .assertThat()
            .hasStatus(HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/200/updateHotel_01.json")
    void updateHotel_200(String filename) {
        given(hotelConvertor.toCommand(any(HotelUpdateRequest.class))).willReturn(mock(HotelUpdateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.update(any(Long.class), any(HotelUpdateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
            .uri("/api/hotels/{hotelId}", 1)
            .content(TestUtils.loadJson(filename))
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.OK)
            .hasContentType(MediaType.APPLICATION_JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/400/updateHotel_01.json")
    void updateHotel_400(String filename) {
        given(hotelConvertor.toCommand(any(HotelUpdateRequest.class))).willReturn(mock(HotelUpdateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.update(any(Long.class), any(HotelUpdateCommand.class))).willReturn(mock(Hotel.class));

        this.mvc.post()
            .uri("/api/hotels/{hotelId}", 1)
            .content(TestUtils.loadJson(filename))
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.BAD_REQUEST)
            .hasContentType(MediaType.APPLICATION_JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/200/updateHotel_01.json")
    void updateHotel_404(String filename) {
        given(hotelConvertor.toCommand(any(HotelUpdateRequest.class))).willReturn(mock(HotelUpdateCommand.class));
        given(hotelConvertor.toDto(any(Hotel.class))).willReturn(mock(HotelDto.class));
        given(hotelService.update(any(Long.class), any(HotelUpdateCommand.class))).willThrow(NoDomainEntityException.class);

        this.mvc.post()
            .uri("/api/hotels/{hotelId}", 1)
            .content(TestUtils.loadJson(filename))
            .contentType(MediaType.APPLICATION_JSON)
            .assertThat()
            .hasStatus(HttpStatus.NOT_FOUND);
    }

}
