package com.eleven.booking.endpoint.web.inner;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.booking.api.endpoint.core.HotelEndpoints;
import com.eleven.booking.api.endpoint.internal.BookingClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AsInternalApi
@Tag(name = HotelEndpoints.Tags.BOOKING)
@RequiredArgsConstructor
public class BookingInnerApi implements BookingClient {

}
