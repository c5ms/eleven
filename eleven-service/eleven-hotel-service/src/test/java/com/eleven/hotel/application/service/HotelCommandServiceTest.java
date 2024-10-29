package com.eleven.hotel.application.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelCommandServiceTest {

    @Autowired
    HotelService hotelService;

    @Test
    void getHotel() {
//        RegisterHotelCommand command = new RegisterHotelCommand().setHotelName("test hotel name")
//            .setHotelTel("test hotel tel")
//            .setHotelDescription("test hotel description")
//            .setHotelRoomNumber(1024)
//            .setHotelHeadPicUrl("test hotel head pic url")
//            .setStaffName("test staff name")
//            .setStaffTel("test staff tel");
//        RegisterDto registerDto = hotelService.register(command);
//        System.out.println(JSONUtil.toJsonStr(registerDto));
    }

    @Test
    void register() {
    }

    @Test
    void review() {
    }
}
