package com.eleven.domain.hotel;

import com.eleven.base.AsConvertorTest;
import com.eleven.core.configure.ElevenCoreConfigure;
import com.eleven.interfaces.hotel.HotelConvertor;
import com.eleven.interfaces.hotel.request.HotelCreateRequest;
import com.eleven.interfaces.hotel.request.HotelUpdateRequest;
import com.eleven.domain.hotel.values.Address;
import com.eleven.domain.hotel.values.CheckPolicy;
import com.eleven.domain.hotel.values.HotelBasic;
import com.eleven.domain.hotel.values.Position;
import com.eleven.interfaces.hotel.vo.AddressVo;
import com.eleven.interfaces.hotel.vo.CheckPolicyVo;
import com.eleven.interfaces.hotel.vo.HotelBasicVo;
import com.eleven.interfaces.hotel.vo.PositionVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalTime;
import java.time.YearMonth;

@AsConvertorTest
@Import(ElevenCoreConfigure.class)
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

    @Test
    void toCreateHotelCommand() {
        var request = new HotelCreateRequest()
                .setAddress(new AddressVo()
                        .setAddress("address")
                        .setCity("city")
                        .setCountry("country")
                        .setProvince("province")
                        .setLocation("location"))
                .setPosition(new PositionVo()
                        .setLatitude(100.1D)
                        .setLongitude(200.2D))
                .setBasic(new HotelBasicVo()
                        .setName("name")
                        .setDescription("description")
                        .setEmail("test@test.com")
                        .setPhone("phone")
                        .setWhenBuilt(YearMonth.of(2021, 10))
                        .setLastRenovation(YearMonth.of(2021, 10))
                        .setBuildingArea(100)
                        .setStarRating(5)
                        .setTotalRoomQuantity(20))
                .setCheckPolicy(new CheckPolicyVo()
                        .setCheckInTime("10:00")
                        .setCheckOutTime("11:00")
                );

        var command = hotelConvertor.toCommand(request);

        Assertions.assertNotNull(command);
        Assertions.assertNotNull(command.getAddress());
        Assertions.assertEquals("country", command.getAddress().getCountry());
        Assertions.assertEquals("city", command.getAddress().getCity());
        Assertions.assertEquals("province", command.getAddress().getProvince());
        Assertions.assertEquals("location", command.getAddress().getLocation());
        Assertions.assertEquals("address", command.getAddress().getAddress());
        Assertions.assertNotNull(command.getBasic());
        Assertions.assertEquals("name", command.getBasic().getName());
        Assertions.assertEquals("description", command.getBasic().getDescription());
        Assertions.assertEquals("phone", command.getBasic().getPhone());
        Assertions.assertEquals(YearMonth.of(2021, 10), command.getBasic().getWhenBuilt());
        Assertions.assertEquals(YearMonth.of(2021, 10), command.getBasic().getLastRenovation());
        Assertions.assertEquals(20, command.getBasic().getTotalRoomQuantity());
        Assertions.assertEquals("test@test.com", command.getBasic().getEmail());
        Assertions.assertNotNull(command.getPosition());
        Assertions.assertEquals(100.1D, command.getPosition().getLatitude());
        Assertions.assertEquals(200.2D, command.getPosition().getLongitude());
        Assertions.assertEquals(LocalTime.of(10, 0), command.getCheckPolicy().getCheckInTime());
        Assertions.assertEquals(LocalTime.of(11, 0), command.getCheckPolicy().getCheckOutTime());
    }


    @Test
    void toUpdateHotelCommand() {
        var request = new HotelUpdateRequest()
                .setAddress(new AddressVo()
                        .setAddress("address")
                        .setCity("city")
                        .setCountry("country")
                        .setProvince("province")
                        .setLocation("location"))
                .setPosition(new PositionVo()
                        .setLatitude(100.1D)
                        .setLongitude(200.2D))
                .setBasic(new HotelBasicVo()
                        .setName("name")
                        .setDescription("description")
                        .setEmail("test@test.com")
                        .setPhone("phone")
                        .setWhenBuilt(YearMonth.of(2021, 10))
                        .setLastRenovation(YearMonth.of(2021, 10))
                        .setBuildingArea(100)
                        .setStarRating(5)
                        .setTotalRoomQuantity(20))
                .setCheckPolicy(new CheckPolicyVo()
                        .setCheckInTime("10:00")
                        .setCheckOutTime("11:00")
                );

        var command = hotelConvertor.toCommand(request);

        Assertions.assertNotNull(command);
        Assertions.assertNotNull(command.getAddress());
        Assertions.assertEquals("country", command.getAddress().getCountry());
        Assertions.assertEquals("city", command.getAddress().getCity());
        Assertions.assertEquals("province", command.getAddress().getProvince());
        Assertions.assertEquals("location", command.getAddress().getLocation());
        Assertions.assertEquals("address", command.getAddress().getAddress());
        Assertions.assertNotNull(command.getBasic());
        Assertions.assertEquals("name", command.getBasic().getName());
        Assertions.assertEquals("description", command.getBasic().getDescription());
        Assertions.assertEquals("phone", command.getBasic().getPhone());
        Assertions.assertEquals(YearMonth.of(2021, 10), command.getBasic().getWhenBuilt());
        Assertions.assertEquals(YearMonth.of(2021, 10), command.getBasic().getLastRenovation());
        Assertions.assertEquals(20, command.getBasic().getTotalRoomQuantity());
        Assertions.assertEquals("test@test.com", command.getBasic().getEmail());
        Assertions.assertNotNull(command.getPosition());
        Assertions.assertEquals(100.1D, command.getPosition().getLatitude());
        Assertions.assertEquals(200.2D, command.getPosition().getLongitude());
        Assertions.assertEquals(LocalTime.of(10, 0), command.getCheckPolicy().getCheckInTime());
        Assertions.assertEquals(LocalTime.of(11, 0), command.getCheckPolicy().getCheckOutTime());
    }

}
