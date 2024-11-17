package com.eleven.hotel.interfaces;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestMvc {


    @Test
    void registerHotelTest() {
//        RegisterHotelCommand command = new RegisterHotelCommand().setHotelName("test hotel name")
//            .setHotelTel("test hotel tel")
//            .setHotelDescription("test hotel description")
//            .setHotelRoomNumber(1024)
//            .setHotelHeadPicUrl("test hotel head pic url")
//            .setStaffName("test staff name")
//            .setStaffTel("test staff tel");
//        RegisterDto registerDto = registerMerchantApi.register(command);
//        Assertions.assertEquals(command.getHotelName(), registerDto.getHotelName());
//        Assertions.assertEquals(RegisterState.UNDER_REVIEW, registerDto.getReviewStatus());
    }

}
