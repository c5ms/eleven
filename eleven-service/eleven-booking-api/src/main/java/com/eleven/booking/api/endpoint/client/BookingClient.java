package com.eleven.booking.api.endpoint.client;

import com.eleven.core.configure.ElevenRestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;

@Primary
@FeignClient(value = "booking", path = ElevenRestConfiguration.API_PREFIX_INTERNAL)
public interface BookingClient {

}
