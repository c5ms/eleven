package com.eleven.demo.endpoint.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest
public class Test001 {

    @Test
    public void givenRequest_whenFetchTaxiFareRateCard_thanOK() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String fare = testRestTemplate.getForObject("http://localhost:8080/demo/03", String.class);
        assertThat(fare, equalTo("200"));
    }
}
