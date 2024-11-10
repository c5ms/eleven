package com.eleven.hotel.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureMockMvc
class TestMvcWithHttp {

    @Test
    void exampleTest(@Autowired WebTestClient webClient) {
        webClient
            .get().uri("/api/admin/hotels/{hotelId}", "037320241004154453000001")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("{\"id\":\"037320241004154453000001\",\"name\":\"test hotel name\",\"tel\":\"test hotel tel\",\"description\":\"test hotel description\",\"headPicUrl\":\"test hotel head pic url\",\"status\":\"UNDER_REVIEW\"}");
    }

}
