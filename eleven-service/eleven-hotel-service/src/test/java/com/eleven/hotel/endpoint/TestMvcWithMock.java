package com.eleven.hotel.endpoint;

import com.eleven.hotel.api.application.model.HotelDto;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.endpoint.resource.RegisterAdminApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterAdminApi.class)
class TestMvcWithMock {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelService hotelService;

    @Test
    @WithMockUser(roles = "ROLE_HOTEL_STAFF")
    void testExample() throws Exception {

//        var hotel = Hotel.create()
//        given(this.hotelService.get(Mockito.any())).willReturn(Optional.of(hotel));
//
//        this.mvc.perform(get("/api/admin/hotels/{hotelId}", "037320241004154453000001")
//                .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().string("{\"id\":null,\"name\":null,\"tel\":null,\"description\":null,\"headPicUrl\":null,\"status\":null}"));
    }

}
