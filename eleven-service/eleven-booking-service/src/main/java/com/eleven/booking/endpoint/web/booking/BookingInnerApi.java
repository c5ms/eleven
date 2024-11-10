package com.eleven.booking.endpoint.web.booking;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.booking.api.endpoint.client.BookingClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AsInternalApi
@Tag(name = "book")
@RequiredArgsConstructor
public class BookingInnerApi implements BookingClient {

}
