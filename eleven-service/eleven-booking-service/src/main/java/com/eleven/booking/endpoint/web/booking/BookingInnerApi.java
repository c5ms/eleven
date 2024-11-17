package com.eleven.booking.endpoint.web.booking;

import com.eleven.booking.api.endpoint.client.BookingClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "book")
@RequiredArgsConstructor
public class BookingInnerApi implements BookingClient {

}
