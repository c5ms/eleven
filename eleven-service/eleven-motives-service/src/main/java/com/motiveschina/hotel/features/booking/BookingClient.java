package com.motiveschina.hotel.features.booking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;

@Primary
@FeignClient(value = "booking", path = "/inner")
public interface BookingClient {

}
