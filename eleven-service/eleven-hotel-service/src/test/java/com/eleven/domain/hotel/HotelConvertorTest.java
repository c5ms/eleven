package com.eleven.domain.hotel;

import com.eleven.domain.hotel.values.Address;
import com.eleven.domain.hotel.values.CheckPolicy;
import com.eleven.domain.hotel.values.HotelBasic;
import com.eleven.domain.hotel.values.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.time.YearMonth;

@SpringBootTest(classes = HotelConvertor.class)
class HotelConvertorTest {

    @Autowired
    HotelConvertor hotelConvertor;

    @Test
    void toDto() {
        var hotel = Hotel.builder()
                .basic(HotelBasic.of("test hotel", "test hotel desc", "test@test.com", "87960606", 20, YearMonth.of(2024, 1), YearMonth.of(2024, 5), 5, 900))
                .address(Address.of("china", "liaoning", "dalian", "high tech street", "32#"))
                .position(Position.of(113.00001D, 114.00001D))
                .checkPolicy(CheckPolicy.of(LocalTime.of(12, 0), LocalTime.of(13, 0)))
                .build();

        var dto = hotelConvertor.toDto(hotel);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(hotel.getActive(), dto.getActive());
        Assertions.assertNull(dto.getHotelId());
        Assertions.assertEquals("12:00", dto.getCheckPolicy().getCheckInTime());
        Assertions.assertEquals("13:00", dto.getCheckPolicy().getCheckOutTime());
        Assertions.assertEquals(113.00001D, dto.getPosition().getLatitude());
        Assertions.assertEquals(114.00001D, dto.getPosition().getLongitude());
        Assertions.assertEquals("china", dto.getAddress().getCountry());
        Assertions.assertEquals("liaoning", dto.getAddress().getProvince());
        Assertions.assertEquals("dalian", dto.getAddress().getCity());
        Assertions.assertEquals("high tech street", dto.getAddress().getLocation());
        Assertions.assertEquals("32#", dto.getAddress().getAddress());
        Assertions.assertEquals("test hotel", dto.getBasic().getName());
        Assertions.assertEquals("test hotel desc", dto.getBasic().getDescription());
        Assertions.assertEquals("87960606", dto.getBasic().getPhone());
        Assertions.assertEquals(900, dto.getBasic().getBuildingArea());
        Assertions.assertEquals(5, dto.getBasic().getStarRating());
        Assertions.assertEquals(YearMonth.of(2024, 1), dto.getBasic().getWhenBuilt());
        Assertions.assertEquals(YearMonth.of(2024, 5), dto.getBasic().getLastRenovation());
        Assertions.assertEquals(20, dto.getBasic().getTotalRoomQuantity());
        Assertions.assertEquals("test@test.com", dto.getBasic().getEmail());
    }

}
