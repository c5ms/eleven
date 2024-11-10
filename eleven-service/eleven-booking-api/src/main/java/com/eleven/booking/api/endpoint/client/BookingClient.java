package com.eleven.booking.api.endpoint.client;

import com.eleven.core.web.WebConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;

@Primary
@FeignClient(value = "booking", path = WebConstants.API_PREFIX_INTERNAL)
public interface BookingClient {

}
