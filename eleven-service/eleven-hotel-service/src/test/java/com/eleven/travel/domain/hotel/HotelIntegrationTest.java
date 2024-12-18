package com.eleven.travel.domain.hotel;

import com.eleven.travel.base.AsIntegrationTest;
import com.eleven.travel.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@AsIntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
public class HotelIntegrationTest {

    @Autowired
    MockMvcTester mvc;

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/200/createHotel_01.json")
    void createHotel_200(String filename) {
      this.mvc.post()
                .uri("/api/hotels")
                .content(TestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void queryHotel_200() {
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
        this.mvc.get()
                .uri("/api/hotels/{hotelId}", 1)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .hasContentType(MediaType.APPLICATION_JSON);
    }

    @ParameterizedTest
    @ValueSource(strings = "/hotel/request/200/updateHotel_01.json")
    void updateHotel_200(String filename) {
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
        this.mvc.post()
                .uri("/api/hotels/{hotelId}", 1)
                .content(TestUtils.loadJson(filename))
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasContentType(MediaType.APPLICATION_JSON);
    }

}
